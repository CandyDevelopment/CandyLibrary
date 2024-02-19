package fit.d6.candy.visual.scoreboard;

import fit.d6.candy.api.visual.scoreboard.Objective;
import fit.d6.candy.api.visual.scoreboard.Score;
import fit.d6.candy.api.visual.scoreboard.ScoreContent;
import fit.d6.candy.api.visual.scoreboard.Scoreboard;
import fit.d6.candy.nms.NmsAccessor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class BukkitScore implements Score {

    private final Object original;

    private final BukkitScoreboard scoreboard;
    private final BukkitObjective objective;
    private final BukkitScoreContent scoreContent;

    public BukkitScore(BukkitScoreboard scoreboard, BukkitObjective objective, BukkitScoreContent scoreContent) {
        this.scoreboard = scoreboard;
        this.objective = objective;
        this.scoreContent = scoreContent;

        this.original = NmsAccessor.getAccessor().createNewScoreAccess(scoreboard.getOriginal(), objective.getOriginal(), scoreContent.getOriginal());
    }

    @Override
    public @NotNull Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    @Override
    public @NotNull Objective getObjective() {
        return this.objective;
    }

    @Override
    public @NotNull ScoreContent getContent() {
        return this.scoreContent;
    }

    @Override
    public int getScore() {
        return NmsAccessor.getAccessor().scoreboardScoreAccessGet(this.original);
    }

    @Override
    public void setScore(int score) {
        NmsAccessor.getAccessor().scoreboardScoreAccessSet(this.original, score);
    }

    @Override
    public int addScore(int amount) {
        return NmsAccessor.getAccessor().scoreboardScoreAccessAdd(this.original, amount);
    }

    @Override
    public int incrementScore() {
        return NmsAccessor.getAccessor().scoreboardScoreAccessIncrement(this.original);
    }

    @Override
    public void resetScore() {
        NmsAccessor.getAccessor().scoreboardScoreAccessReset(this.original);
    }

    public void updateDisplayName(Component displayName) {
        NmsAccessor.getAccessor().scoreboardScoreAccessUpdateDisplayName(this.original, displayName);
    }

}
