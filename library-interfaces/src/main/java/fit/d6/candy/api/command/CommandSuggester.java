package fit.d6.candy.api.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface CommandSuggester {

    void suggests(@NotNull CommandContext context, @NotNull CommandArgumentHelper argument, @NotNull CommandSuggestion suggestion) throws CommandSyntaxException;

}
