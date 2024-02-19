package fit.d6.candy.api.visual.scoreboard;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface ScoreContent {

    @NotNull
    Scoreboard getScoreboard();

    @NotNull
    String getName();

    @NotNull
    Component getDisplayName();

    @NotNull
    ScoreContent setDisplayName(@NotNull Component displayName);

    @NotNull
    Component getPrefix();

    @NotNull
    ScoreContent setPrefix(@NotNull Component prefix);

    @NotNull
    Component getSuffix();

    @NotNull
    ScoreContent setSuffix(@NotNull Component suffix);

}
