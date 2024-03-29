package fit.d6.candy.api.visual.scoreboard;

import fit.d6.candy.api.annotation.FoliaSupport;
import fit.d6.candy.api.visual.VisualService;
import org.jetbrains.annotations.NotNull;

@FoliaSupport
public interface ScoreboardManager {

    @NotNull
    Scoreboard createScoreboard(@NotNull String name);

    @NotNull
    static ScoreboardManager getManager() {
        return VisualService.getService().getScoreboardManager();
    }

}
