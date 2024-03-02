package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.CloseListener;
import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.MovingItemListener;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiRenderer;
import fit.d6.candy.api.gui.slot.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BukkitNormalGui extends BukkitGui implements NormalGui {

    private final Plugin plugin;
    private final int lines;
    private final Map<Integer, BukkitSlot> initialization;
    private final Component defaultTitle;

    private final Set<CloseListener> closeListeners;
    private final Set<MovingItemListener> movingItemListeners;

    public BukkitNormalGui(Plugin plugin, int lines, Map<Integer, BukkitSlot> initialization, Component defaultTitle, Set<CloseListener> closeListeners, Set<MovingItemListener> movingItemListeners) {
        this.plugin = plugin;
        this.lines = lines;
        this.initialization = new HashMap<>(initialization);
        this.defaultTitle = defaultTitle;
        this.closeListeners = closeListeners;
        this.movingItemListeners = movingItemListeners;
    }

    @Override
    public @NotNull Map<Integer, Slot> getInitialization() {
        return new HashMap<>(initialization);
    }

    @Override
    public @NotNull NormalGuiRenderer prepare(@NotNull GuiAudience audience) {
        return new BukkitNormalGuiRenderer(this.plugin, this, this.lines, this.initialization, this.defaultTitle, audience, null);
    }

    @Override
    public @NotNull Set<CloseListener> listCloseListeners() {
        return new HashSet<>(this.closeListeners);
    }

    @Override
    public @NotNull Set<MovingItemListener> listMovingItemListeners() {
        return new HashSet<>(this.movingItemListeners);
    }

    @Override
    public @NotNull Component getDefaultTitle() {
        return this.defaultTitle;
    }
}
