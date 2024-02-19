package fit.d6.candy.api.visual;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import fit.d6.candy.api.visual.scoreboard.ScoreboardManager;
import fit.d6.candy.api.visual.tablist.TabListManager;
import org.jetbrains.annotations.NotNull;

public interface VisualService extends Service {

    @NotNull
    TabListManager getTabListManager();

    @NotNull
    ScoreboardManager getScoreboardManager();

    @NotNull
    static VisualService getService() {
        return CandyLibrary.getLibrary().getService(VisualService.class);
    }

}
