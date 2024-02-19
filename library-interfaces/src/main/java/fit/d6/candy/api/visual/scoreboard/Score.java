package fit.d6.candy.api.visual.scoreboard;

import org.jetbrains.annotations.NotNull;

public interface Score {

    @NotNull
    Scoreboard getScoreboard();

    @NotNull
    Objective getObjective();

    @NotNull
    ScoreContent getContent();

    int getScore();

    void setScore(int score);

    int addScore(int amount);

    int incrementScore();

    void resetScore();

}
