package fit.d6.candy.visual;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.visual.VisualService;
import fit.d6.candy.api.visual.scoreboard.ScoreboardManager;
import fit.d6.candy.api.visual.tablist.TabListManager;
import fit.d6.candy.visual.scoreboard.BukkitScoreboardManager;
import fit.d6.candy.visual.tablist.BukkitTabListManager;
import org.jetbrains.annotations.NotNull;

public class BukkitVisualService extends BukkitService implements VisualService {

    private final TabListManager tabListManager = new BukkitTabListManager();
    private final ScoreboardManager scoreboardManager = new BukkitScoreboardManager();

    @Override
    public @NotNull String getId() {
        return "visual";
    }

    @Override
    public @NotNull TabListManager getTabListManager() {
        return this.tabListManager;
    }

    @Override
    public @NotNull ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

}
