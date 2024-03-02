package fit.d6.candy.bukkit.visual.scoreboard;

import fit.d6.candy.api.visual.scoreboard.Objective;
import fit.d6.candy.api.visual.scoreboard.Score;
import fit.d6.candy.api.visual.scoreboard.ScoreContent;
import fit.d6.candy.api.visual.scoreboard.Scoreboard;
import fit.d6.candy.bukkit.exception.VisualException;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitObjective implements Objective {

    private final Object original;
    private final BukkitScoreboard scoreboard;
    private final String name;
    private Component displayName;

    private final Map<BukkitScoreContent, BukkitScore> contents = new HashMap<>();

    public BukkitObjective(Object original, BukkitScoreboard scoreboard, String name, Component displayName) {
        this.original = original;
        this.scoreboard = scoreboard;
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public @NotNull Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.displayName;
    }

    @Override
    public void setDisplayName(@NotNull Component displayName) {
        this.displayName = displayName;
        NmsAccessor.getAccessor().updateObjectiveDisplayName(this.original, displayName);
    }

    @Override
    public @NotNull Score addContent(@NotNull ScoreContent content) {
        if (this.contents.containsKey(((BukkitScoreContent) content)))
            throw new VisualException("This objective has already owned this content");
        BukkitScore score = new BukkitScore(this.scoreboard, this, (BukkitScoreContent) content);
        ((BukkitScoreContent) content).scores.add(score);
        this.contents.put((BukkitScoreContent) content, score);
        return score;
    }

    @Override
    public void removeContent(@NotNull ScoreContent content) {
        if (!this.contents.containsKey(((BukkitScoreContent) content)))
            throw new VisualException("This content not exists in this objective");
        BukkitScore score = this.contents.remove(((BukkitScoreContent) content));
        ((BukkitScoreContent) content).scores.remove(score);
        NmsAccessor.getAccessor().removeScoreAccess(this.scoreboard.getOriginal(), this.getOriginal(), ((BukkitScoreContent) content).getOriginal());
    }

    @Override
    public List<Score> listContents() {
        return new ArrayList<>(this.contents.values());
    }

    public void removeAllContents() {
        new ArrayList<>(this.contents.keySet()).forEach(this::removeContent);
    }

    public Object getOriginal() {
        return original;
    }

}
