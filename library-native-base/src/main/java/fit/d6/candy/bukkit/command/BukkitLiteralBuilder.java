package fit.d6.candy.bukkit.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import fit.d6.candy.api.command.*;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BukkitLiteralBuilder extends BukkitCommandBuilder<LiteralArgumentBuilder<Object>> {

    private final LiteralArgumentBuilder<Object> brigadier;

    private final String name;
    private final List<CommandNode> children = new ArrayList<>();
    private CommandExecutor executor;
    private CommandExecutor playerExecutor;
    private Command redirection;
    private Requirement requirement;

    @SuppressWarnings("unchecked")
    public BukkitLiteralBuilder(String name) {
        this.brigadier = (LiteralArgumentBuilder<Object>) NmsAccessor.getAccessor().createLiteralCommand(name);
        this.name = name;
    }

    @Override
    public @NotNull CommandBuilder then(CommandNode nextNode) {
        if (nextNode instanceof BukkitCommand) {
            this.brigadier.then(((BukkitCommand) nextNode).toBrigadier());
        } else if (nextNode instanceof BukkitCommandBuilder<?>) {
            this.brigadier.then(((BukkitCommandBuilder<?>) nextNode).toBrigadier());
        } else {
            return this;
        }
        this.children.add(nextNode);
        return this;
    }

    @Override
    public @NotNull CommandBuilder requires(Requirement requirement) {
        this.requirement = requirement;
        if (requirement == null) {
            this.brigadier.requires(it -> true);
        } else {
            this.brigadier.requires(source -> this.requirement.check(NmsAccessor.getAccessor().commandSourceStackGetBukkitSender(source)));
        }
        return this;
    }

    @Override
    public @NotNull CommandBuilder executes(CommandExecutor executor) {
        this.executor = executor;
        this.checkExecutor();
        return this;
    }

    @Override
    public @NotNull CommandBuilder executesPlayer(CommandExecutor executor) {
        this.playerExecutor = executor;
        this.checkExecutor();
        return this;
    }

    private void checkExecutor() {
        if (this.executor == null && this.playerExecutor == null) {
            this.brigadier.executes(null);
        } else {
            this.brigadier.executes(context -> {
                CommandArgumentHelper argumentHelper = new BukkitCommandArgumentHelper(context);
                CommandContext candyContext = new BukkitCommandContext(context, argumentHelper);
                if (this.playerExecutor != null && NmsAccessor.getAccessor().commandSourceStackIsPlayer(context.getSource())) {
                    return this.playerExecutor.executes(candyContext, argumentHelper);
                } else if (this.executor != null) {
                    return this.executor.executes(candyContext, argumentHelper);
                } else {
                    throw NmsAccessor.getAccessor().commandSourceStackNotPlayerException().create();
                }
            });
        }
    }

    @Override
    public @NotNull CommandBuilder suggests(CommandSuggester suggester) {
        return this;
    }

    @Override
    public @NotNull CommandBuilder redirects(Command redirection) {
        this.redirection = redirection;
        if (redirection != null) {
            this.brigadier.redirect(((BukkitCommand) redirection).toBrigadier());
        } else {
            this.brigadier.redirect(null);
        }
        return this;
    }

    @Override
    public LiteralArgumentBuilder<Object> toBrigadier() {
        return brigadier;
    }

}
