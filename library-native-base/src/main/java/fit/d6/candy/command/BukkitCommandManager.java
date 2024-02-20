package fit.d6.candy.command;

import com.mojang.brigadier.tree.CommandNode;
import fit.d6.candy.api.command.*;
import fit.d6.candy.command.argument.BukkitArgumentType;
import fit.d6.candy.exception.CommandException;
import fit.d6.candy.nms.NmsAccessor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BukkitCommandManager implements CommandManager, Listener {

    private static final Map<String, LinkedHashMap<Command, CommandOptions>> REGISTERED = new HashMap<>();

    @Override
    public @NotNull CommandOptions createOptions() {
        return new BukkitCommandOptions();
    }

    @Override
    public @NotNull CommandBuilder createCommand(@NotNull String name) {
        if (name.isEmpty())
            throw new CommandException("Name cannot be empty");
        return new BukkitLiteralBuilder(name.toLowerCase());
    }

    @Override
    public @NotNull CommandBuilder createCommand(@NotNull String name, @NotNull ArgumentType argumentType) {
        if (name.isEmpty())
            throw new CommandException("Name cannot be empty");
        return new BukkitArgumentBuilder(name.toLowerCase(), (BukkitArgumentType) argumentType);
    }

    @Override
    public @NotNull Command register(@NotNull CommandBuilder builder) {
        return this.register("candy", builder);
    }

    @Override
    public @NotNull Command register(@NotNull String prefix, @NotNull CommandBuilder builder) {
        return this.register(prefix, builder, new BukkitCommandOptions());
    }

    @Override
    public @NotNull Command register(@NotNull String prefix, @NotNull CommandBuilder builder, @NotNull CommandOptions options) {
        if (prefix.isEmpty())
            throw new CommandException("Prefix cannot be empty");
        prefix = prefix.toLowerCase();
        if (!REGISTERED.containsKey(prefix))
            REGISTERED.put(prefix, new LinkedHashMap<>());
        CommandNode<Object> commandNode = ((BukkitCommandBuilder<?>) builder).toBrigadier().build();
        if (builder instanceof BukkitLiteralBuilder) {
            org.bukkit.command.Command wrappedCommand = (org.bukkit.command.Command) NmsAccessor.getAccessor().createVanillaCommandWrapper(null, commandNode);
            BukkitCommandOptions commandOptions = (BukkitCommandOptions) options;
            wrappedCommand.setAliases(commandOptions.getAliases());
            wrappedCommand.setDescription(commandOptions.getDescription());
            if (commandOptions.getUsage() != null)
                wrappedCommand.setUsage(commandOptions.getUsage());
            Bukkit.getCommandMap().register(prefix, wrappedCommand);
        }
        Command command = new BukkitCommand(commandNode);
        REGISTERED.get(prefix).put(command, options);
        return command;
    }

    @EventHandler
    public void onLoad(ServerLoadEvent event) {
        // Name this with bukkit because actually bukkit created a new one replace the vanilla one
        Object bukkitCommands = NmsAccessor.getAccessor().getBukkitCommands();

        CommandMap commandMap = Bukkit.getCommandMap();

        Map<String, org.bukkit.command.Command> knownCommands = commandMap.getKnownCommands();

        for (String prefix : REGISTERED.keySet()) {
            for (Map.Entry<Command, CommandOptions> entry : REGISTERED.get(prefix).entrySet()) {
                Command command = entry.getKey();
                BukkitCommandOptions options = (BukkitCommandOptions) entry.getValue();
                CommandNode<Object> commandNode = ((BukkitCommand) command).toBrigadier();

                org.bukkit.command.Command vanillaWrapper = knownCommands.get(command.getName());
                org.bukkit.command.Command prefixVanillaWrapper = knownCommands.get(prefix + ":" + command.getName());

                org.bukkit.command.Command commandWrapper = (org.bukkit.command.Command) NmsAccessor.getAccessor().createVanillaCommandWrapper(bukkitCommands, commandNode);

                commandWrapper.setAliases(options.getAliases());
                commandWrapper.setDescription(options.getDescription());
                if (options.getUsage() != null)
                    commandWrapper.setUsage(options.getUsage());

                if (vanillaWrapper != null) {
                    knownCommands.put(command.getName(), commandWrapper);
                }

                if (prefixVanillaWrapper != null) {
                    knownCommands.put(prefix + ":" + command.getName(), commandWrapper);
                }

                for (String alias : options.getAliases()) {

                    org.bukkit.command.Command aliasVanillaWrapper = knownCommands.get(alias);
                    org.bukkit.command.Command prefixAliasVanillaWrapper = knownCommands.get(prefix + ":" + alias);

                    if (aliasVanillaWrapper != null) {
                        knownCommands.put(command.getName(), commandWrapper);
                    }

                    if (prefixAliasVanillaWrapper != null) {
                        knownCommands.put(prefix + ":" + command.getName(), commandWrapper);
                    }

                }
            }
        }
    }

}
