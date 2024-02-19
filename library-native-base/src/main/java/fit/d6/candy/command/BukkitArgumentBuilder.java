package fit.d6.candy.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import fit.d6.candy.api.command.*;
import fit.d6.candy.command.argument.BukkitArgumentType;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class BukkitArgumentBuilder extends BukkitCommandBuilder<RequiredArgumentBuilder<Object, Object>> {

    private final RequiredArgumentBuilder<Object, Object> brigadier;

    private final String name;
    private final List<CommandNode> children = new ArrayList<>();
    private CommandExecutor executor;
    private CommandExecutor playerExecutor;
    private CommandSuggester suggester;
    private Command redirection;
    private Requirement requirement;

    public BukkitArgumentBuilder(String name, BukkitArgumentType argumentType) {
        this.brigadier = (RequiredArgumentBuilder<Object, Object>) NmsAccessor.getAccessor().createArgumentCommand(name, argumentType.toBrigadier());

        this.name = name;

        if (argumentType.getType() == ArgumentTypes.SUMMONABLE_ENTITY_TYPE)
            this.brigadier.suggests((SuggestionProvider<Object>) NmsAccessor.getAccessor().getSummonableEntitiesProvider());

        NmsAccessor.getAccessor().trySuggests(this.brigadier, argumentType, argumentType.toBrigadier());
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
        this.suggester = suggester;
        if (suggester == null) {
            this.brigadier.suggests(null);
        } else {
            this.brigadier.suggests((commandContext, suggestionsBuilder) -> {
                CommandArgumentHelper argument = new BukkitCommandArgumentHelper(commandContext);
                CommandContext context = new BukkitCommandContext(commandContext, argument);
                CommandSuggestion suggestion = new BukkitCommandSuggestion(suggestionsBuilder);

                this.suggester.suggests(context, argument, suggestion);

                return suggestionsBuilder.buildFuture();
            });
        }
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
    public ArgumentBuilder<Object, ?> toBrigadier() {
        return this.brigadier;
    }

}
