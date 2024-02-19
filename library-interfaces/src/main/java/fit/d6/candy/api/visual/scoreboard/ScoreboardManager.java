package fit.d6.candy.api.visual.scoreboard;

import org.jetbrains.annotations.NotNull;

public interface ScoreboardManager {

    @NotNull
    Scoreboard createScoreboard(@NotNull String name);

}
