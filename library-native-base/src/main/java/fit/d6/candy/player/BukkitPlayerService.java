package fit.d6.candy.player;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.player.PlayerService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class BukkitPlayerService extends BukkitService implements PlayerService {

    private final BukkitPlayerManager playerManager = new BukkitPlayerManager();
    private final BukkitSkinManager skinManager = new BukkitSkinManager();

    @Override
    public @NotNull String getId() {
        return "player";
    }

    @Override
    public void enable(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this.playerManager, plugin);
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.playerManager.showedUuids.put(player, new ArrayList<>());
            this.playerManager.showedUuids.get(player).addAll(Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).toList());

            this.playerManager.showedObjectives.put(player, new ArrayList<>());
            this.playerManager.showedScoreContents.put(player, new HashMap<>());
        }
    }

    @Override
    public void disable(Plugin plugin) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                this.playerManager.resetTabList(player);
                this.playerManager.clearScoreboard(player);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public @NotNull BukkitPlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public @NotNull BukkitSkinManager getSkinManager() {
        return skinManager;
    }

}
