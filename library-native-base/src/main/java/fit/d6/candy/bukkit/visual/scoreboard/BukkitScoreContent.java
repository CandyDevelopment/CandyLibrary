package fit.d6.candy.bukkit.visual.scoreboard;

import fit.d6.candy.api.visual.scoreboard.ScoreContent;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BukkitScoreContent implements ScoreContent {

    private final Object original;
    private final Object playerTeam;

    private final BukkitScoreboard scoreboard;
    private final String name;
    private Component displayName;
    private Component prefix;
    private Component suffix;

    final List<BukkitScore> scores = new ArrayList<>();

    public BukkitScoreContent(BukkitScoreboard scoreboard, String name, Component displayName) {
        this.scoreboard = scoreboard;
        this.name = name;
        this.displayName = displayName;

        this.original = NmsAccessor.getAccessor().createNewScoreboardScoreHolder(name, this);
        this.playerTeam = NmsAccessor.getAccessor().createNewScoreboardPlayerTeam(scoreboard.getOriginal(), name, this.original);
    }

    @NotNull
    @Override
    public BukkitScoreboard getScoreboard() {
        return scoreboard;
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
    public @NotNull ScoreContent setDisplayName(@NotNull Component displayName) {
        this.displayName = displayName;
        this.scores.forEach(it -> it.updateDisplayName(displayName));
        return this;
    }

    @Override
    public @NotNull Component getPrefix() {
        return this.prefix;
    }

    @Override
    public @NotNull ScoreContent setPrefix(@NotNull Component prefix) {
        this.prefix = prefix;
        NmsAccessor.getAccessor().updatePlayerTeamPrefix(this.playerTeam, prefix);
        return this;
    }

    @Override
    public @NotNull Component getSuffix() {
        return this.suffix;
    }

    @Override
    public @NotNull ScoreContent setSuffix(@NotNull Component suffix) {
        this.suffix = suffix;
        NmsAccessor.getAccessor().updatePlayerTeamSuffix(this.playerTeam, suffix);
        return this;
    }

    public Object getOriginal() {
        return original;
    }

    public Object getPlayerTeam() {
        return playerTeam;
    }

}
