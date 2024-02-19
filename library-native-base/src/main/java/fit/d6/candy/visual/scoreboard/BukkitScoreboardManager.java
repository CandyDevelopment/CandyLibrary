package fit.d6.candy.visual.scoreboard;

import fit.d6.candy.api.visual.scoreboard.Scoreboard;
import fit.d6.candy.api.visual.scoreboard.ScoreboardManager;
import fit.d6.candy.exception.VisualException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BukkitScoreboardManager implements ScoreboardManager {

    private final Map<String, BukkitScoreboard> scoreboards = new HashMap<>();

    @Override
    public @NotNull Scoreboard createScoreboard(@NotNull String name) {
        if (this.scoreboards.containsKey(name))
            throw new VisualException("The scoreboard has been created");
        BukkitScoreboard scoreboard = new BukkitScoreboard(name);
        this.scoreboards.put(name, scoreboard);
        return scoreboard;
    }

}
