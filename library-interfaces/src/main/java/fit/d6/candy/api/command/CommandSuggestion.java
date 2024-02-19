package fit.d6.candy.api.command;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface CommandSuggestion {

    @NotNull
    String getInput();

    int getStart();

    @NotNull
    String getRemaining();

    @NotNull
    CommandSuggestion suggests(@NotNull String value);

    @NotNull
    CommandSuggestion suggests(@NotNull String value, @NotNull Component tooltip);

    @NotNull
    CommandSuggestion suggests(int value);

    @NotNull
    CommandSuggestion suggests(int value, @NotNull Component tooltip);

}
