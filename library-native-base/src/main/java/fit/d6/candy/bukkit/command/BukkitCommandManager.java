package fit.d6.candy.bukkit.command;

import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import fit.d6.candy.api.command.*;
import fit.d6.candy.bukkit.command.argument.BukkitArgumentType;
import fit.d6.candy.bukkit.exception.CommandException;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

public class BukkitCommandManager implements CommandManager, Listener {

    private final static Field COMMAND_NODE__CHILDREN;
    private final static Field COMMAND_NODE__LIETRALS;

    static {
        try {
            COMMAND_NODE__CHILDREN = CommandNode.class.getDeclaredField("children");
            COMMAND_NODE__LIETRALS = CommandNode.class.getDeclaredField("literals");

            COMMAND_NODE__CHILDREN.setAccessible(true);
            COMMAND_NODE__LIETRALS.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Map<String, LinkedHashMap<Command, CommandOptions>> REGISTERED = new HashMap<>();
    private static final List<String> REGISTERED_NAMES = new ArrayList<>();
    private boolean removeVanillaCommands = false;
    private boolean removePrefixedCommands = false;

    @Override
    public void removeVanillaCommands() {
        this.removeVanillaCommands = true;
    }

    @Override
    public void removePrefixedCommands() {
        this.removePrefixedCommands = true;
    }

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
        REGISTERED_NAMES.add(command.getName());
        REGISTERED.get(prefix).put(command, options);
        return command;
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onLoad(ServerLoadEvent event) throws IllegalAccessException {
        // Name this with bukkit because actually bukkit created a new one replace the vanilla one
        Object bukkitCommands = NmsAccessor.getAccessor().getBukkitCommands();

        RootCommandNode<?> rootCommandNode = NmsAccessor.getAccessor().getRootCommandNode();
        CommandMap commandMap = Bukkit.getCommandMap();

        Map<String, org.bukkit.command.Command> knownCommands = commandMap.getKnownCommands();

        Map<String, CommandNode<?>> children = (Map<String, CommandNode<?>>) COMMAND_NODE__CHILDREN.get(rootCommandNode);
        Map<String, LiteralCommandNode<?>> literals = (Map<String, LiteralCommandNode<?>>) COMMAND_NODE__LIETRALS.get(rootCommandNode);

        if (this.removeVanillaCommands) {
            for (Map.Entry<String, org.bukkit.command.Command> entry : new ArrayList<>(knownCommands.entrySet())) {
                if (REGISTERED_NAMES.contains(entry.getKey()))
                    continue;
                if (NmsAccessor.getAccessor().isVanillaCommandWrapper(entry.getValue())) {
                    knownCommands.remove(entry.getKey());
                    literals.remove(entry.getKey());
                    if (children.get(entry.getKey()) instanceof LiteralCommandNode) {
                        children.remove(entry.getKey());
                    }
                }
            }
        }

        for (String prefix : REGISTERED.keySet()) {
            for (Map.Entry<Command, CommandOptions> entry : REGISTERED.get(prefix).entrySet()) {
                Command command = entry.getKey();
                BukkitCommandOptions options = (BukkitCommandOptions) entry.getValue();
                CommandNode<Object> commandNode = ((BukkitCommand) command).toBrigadier();

                org.bukkit.command.Command commandWrapper = (org.bukkit.command.Command) NmsAccessor.getAccessor().createVanillaCommandWrapper(bukkitCommands, commandNode);

                commandWrapper.setAliases(options.getAliases());
                commandWrapper.setDescription(options.getDescription());
                if (options.getUsage() != null)
                    commandWrapper.setUsage(options.getUsage());

                knownCommands.put(command.getName(), commandWrapper);
                children.put(command.getName(), commandNode);

                if (!this.removePrefixedCommands) {
                    knownCommands.put(prefix + ":" + command.getName(), commandWrapper);
                    children.put(prefix + ":" + command.getName(), commandNode);
                }

                for (String alias : options.getAliases()) {
                    knownCommands.put(alias, commandWrapper);
                    children.put(alias, commandNode);

                    if (!this.removePrefixedCommands) {
                        knownCommands.put(prefix + ":" + alias, commandWrapper);
                        children.put(prefix + ":" + alias, commandNode);
                    }

                }
            }
        }

        if (this.removePrefixedCommands) {
            for (String key : new ArrayList<>(knownCommands.keySet())) {
                if (key.contains(":")) {
                    knownCommands.remove(key);
                    literals.remove(key);
                    if (children.get(key) instanceof LiteralCommandNode) {
                        children.remove(key);
                    }
                }
            }
        }
    }

}
