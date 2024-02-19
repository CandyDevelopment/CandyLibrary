package fit.d6.candy.api.visual.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface Scoreboard {

    @NotNull
    String getName();

    @NotNull
    Objective getObjective(@NotNull String name);

    @NotNull
    Objective addObjective(@NotNull String name, @NotNull Component displayName);

    boolean hasObjective(@NotNull Objective objective);

    void removeObjective(@NotNull Objective objective);

    @NotNull
    List<@NotNull Objective> listObjectives();

    @NotNull
    List<@NotNull String> listObjectiveNames();

    @NotNull
    ScoreContent createContent(@NotNull Component content);

    boolean hasContent(@NotNull ScoreContent content);

    void display(@NotNull DisplaySlot slot, @NotNull Objective objective);

    void undisplay(@NotNull DisplaySlot slot);

    @NotNull
    Map<@NotNull DisplaySlot, @NotNull Objective> listDisplays();

    @NotNull
    List<@NotNull ScoreContent> listContents();

}
