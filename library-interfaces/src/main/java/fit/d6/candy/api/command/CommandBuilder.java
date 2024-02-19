package fit.d6.candy.api.command;

import org.jetbrains.annotations.NotNull;

public interface CommandBuilder extends CommandNode {

    @NotNull
    CommandBuilder then(CommandNode nextNode);

    @NotNull
    CommandBuilder requires(Requirement requirement);

    @NotNull
    CommandBuilder executes(CommandExecutor executor);

    @NotNull
    CommandBuilder executesPlayer(CommandExecutor executor);

    @NotNull
    CommandBuilder suggests(CommandSuggester suggester);

    @NotNull
    CommandBuilder redirects(Command redirection);

}
