package fit.d6.candy.bukkit.visual.scoreboard;

import fit.d6.candy.api.visual.scoreboard.Objective;
import fit.d6.candy.api.visual.scoreboard.ScoreContent;
import fit.d6.candy.api.visual.scoreboard.Scoreboard;
import fit.d6.candy.bukkit.exception.VisualException;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import fit.d6.candy.bukkit.util.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitScoreboard implements Scoreboard {

    private final Object original;

    private final Map<String, BukkitObjective> objectives = new HashMap<>();
    private final Map<DisplaySlot, BukkitObjective> displays = new HashMap<>();

    private final String name;
    private final List<ScoreContent> contents = new ArrayList<>();

    public BukkitScoreboard(@NotNull String name) {
        this.name = name;
        this.original = NmsAccessor.getAccessor().createNewScoreboard();
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public @NotNull Objective getObjective(@NotNull String name) {
        if (!this.objectives.containsKey(name))
            throw new VisualException("The objective with this name not exists");
        return this.objectives.get(name);
    }

    @Override
    public @NotNull Objective addObjective(@NotNull String name, @NotNull Component displayName) {
        if (this.objectives.containsKey(name))
            throw new VisualException("The objective with this name exists");
        BukkitObjective objective = new BukkitObjective(NmsAccessor.getAccessor().createNewScoreboardObjective(this.original, this.name + "_" + name, displayName), this, name, displayName);
        this.objectives.put(name, objective);
        return objective;
    }

    @Override
    public boolean hasObjective(@NotNull Objective objective) {
        return this.objectives.containsValue((BukkitObjective) objective);
    }

    @Override
    public void removeObjective(@NotNull Objective objective) {
        if (!this.objectives.containsKey(objective.getName()))
            throw new VisualException("This objective not belongs to this scoreboard");
        this.objectives.remove(objective.getName()).removeAllContents();
        NmsAccessor.getAccessor().removeScoreboardObjective(this.original, ((BukkitObjective) objective).getOriginal());
    }

    @Override
    public @NotNull List<@NotNull Objective> listObjectives() {
        return new ArrayList<>(this.objectives.values());
    }

    @Override
    public @NotNull List<@NotNull String> listObjectiveNames() {
        return new ArrayList<>(this.objectives.keySet());
    }

    @Override
    public @NotNull ScoreContent createContent(@NotNull Component content) {
        BukkitScoreContent scoreContent = new BukkitScoreContent(this, this.name + "_" + StringUtils.generateRandomString(8) + "_" + System.currentTimeMillis(), content);
        this.contents.add(scoreContent);
        return scoreContent;
    }

    @Override
    public boolean hasContent(@NotNull ScoreContent content) {
        return this.contents.contains(content);
    }

    @Override
    public void display(@NotNull DisplaySlot slot, @NotNull Objective objective) {
        if (!this.objectives.containsKey(objective.getName()))
            throw new VisualException("This objective not belongs to this scoreboard");
        NmsAccessor.getAccessor().updateDisplaySlot(this.original, slot, ((BukkitObjective) objective).getOriginal());
        this.displays.put(slot, (BukkitObjective) objective);
    }

    @Override
    public void undisplay(@NotNull DisplaySlot slot) {
        NmsAccessor.getAccessor().updateDisplaySlot(this.original, slot, null);
        this.displays.remove(slot);
    }

    @Override
    public @NotNull Map<@NotNull DisplaySlot, @NotNull Objective> listDisplays() {
        return new HashMap<>(this.displays);
    }

    @Override
    public @NotNull List<@NotNull ScoreContent> listContents() {
        return new ArrayList<>(this.contents);
    }

    public Object getOriginal() {
        return original;
    }

}
