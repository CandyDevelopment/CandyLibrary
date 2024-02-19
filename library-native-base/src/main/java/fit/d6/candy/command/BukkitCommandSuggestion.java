package fit.d6.candy.command;

import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fit.d6.candy.api.command.CommandSuggestion;
import fit.d6.candy.nms.NmsAccessor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class BukkitCommandSuggestion implements CommandSuggestion {

    private final SuggestionsBuilder suggestionsBuilder;

    public BukkitCommandSuggestion(SuggestionsBuilder suggestionsBuilder) {
        this.suggestionsBuilder = suggestionsBuilder;
    }

    @Override
    public @NotNull String getInput() {
        return this.suggestionsBuilder.getInput();
    }

    @Override
    public int getStart() {
        return this.suggestionsBuilder.getStart();
    }

    @Override
    public @NotNull String getRemaining() {
        return this.suggestionsBuilder.getRemaining();
    }

    @Override
    public @NotNull CommandSuggestion suggests(@NotNull String value) {
        this.suggestionsBuilder.suggest(value);
        return this;
    }

    @Override
    public @NotNull CommandSuggestion suggests(@NotNull String value, @NotNull Component tooltip) {
        this.suggestionsBuilder.suggest(value, NmsAccessor.getAccessor().componentAsVanilla(tooltip));
        return this;
    }

    @Override
    public @NotNull CommandSuggestion suggests(int value) {
        this.suggestionsBuilder.suggest(value);
        return this;
    }

    @Override
    public @NotNull CommandSuggestion suggests(int value, @NotNull Component tooltip) {
        this.suggestionsBuilder.suggest(value, NmsAccessor.getAccessor().componentAsVanilla(tooltip));
        return this;
    }

}
