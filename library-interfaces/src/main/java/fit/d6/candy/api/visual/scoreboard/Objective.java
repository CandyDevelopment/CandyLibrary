package fit.d6.candy.api.visual.scoreboard;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Objective {

    @NotNull
    Scoreboard getScoreboard();

    @NotNull
    String getName();

    @NotNull
    Component getDisplayName();

    void setDisplayName(@NotNull Component displayName);

    @NotNull
    Score addContent(@NotNull ScoreContent content);

    void removeContent(@NotNull ScoreContent content);

    List<Score> listContents();

}
