package fit.d6.candy.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.GuiScene;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BukkitGuiAudience implements GuiAudience {

    private final Player player;
    private GuiScene scene;

    public BukkitGuiAudience(Player player) {
        this.player = player;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    @Override
    public @NotNull String getName() {
        return this.player.getName();
    }

    @Override
    public @Nullable GuiScene getCurrentScene() {
        return this.scene;
    }

    @Override
    public @NotNull Player asBukkit() {
        return this.player;
    }

    public void setScene(GuiScene scene) {
        this.scene = scene;
    }

    public Player getPlayer() {
        return player;
    }

}
