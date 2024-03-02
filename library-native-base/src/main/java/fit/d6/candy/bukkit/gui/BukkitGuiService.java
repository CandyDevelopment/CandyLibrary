package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.GuiManager;
import fit.d6.candy.api.gui.GuiService;
import fit.d6.candy.bukkit.BukkitService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BukkitGuiService extends BukkitService implements GuiService, Listener {

    private final static Map<UUID, BukkitGuiAudience> AUDIENCES = new HashMap<>();

    private final GuiManager guiManager = new BukkitGuiManager();

    @EventHandler
    public void join(PlayerJoinEvent event) {
        AUDIENCES.put(event.getPlayer().getUniqueId(), new BukkitGuiAudience(event.getPlayer()));
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        AUDIENCES.remove(event.getPlayer().getUniqueId()).setScene(null);
    }

    @Override
    public @NotNull String getId() {
        return "gui";
    }

    @Override
    public @NotNull GuiManager getGuiManager() {
        return this.guiManager;
    }

    @Override
    public @NotNull GuiAudience getAudience(@NotNull Player player) {
        return AUDIENCES.get(player.getUniqueId());
    }

    @Override
    public void enable(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        for (Player player : Bukkit.getOnlinePlayers()) {
            AUDIENCES.put(player.getUniqueId(), new BukkitGuiAudience(player));
        }
    }

    @Override
    public void disable(Plugin plugin) {
        for (BukkitGuiAudience audience : AUDIENCES.values()) {
            if (audience.getCurrentScene() != null)
                audience.getPlayer().closeInventory();
            audience.setScene(null);
        }
        AUDIENCES.clear();
    }

}
