package fit.d6.candy.gui;

import fit.d6.candy.api.gui.CloseListener;
import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.MovingItemListener;
import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiRenderer;
import fit.d6.candy.api.gui.anvil.AnvilSlot;
import fit.d6.candy.api.gui.slot.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BukkitAnvilGui extends BukkitGui implements AnvilGui {

    private final Plugin plugin;
    private final Map<AnvilSlot, BukkitSlot> initialization;
    private final Component defaultTitle;

    private final Set<CloseListener> closeListeners;
    private final Set<MovingItemListener> movingItemListeners;

    public BukkitAnvilGui(Plugin plugin, Map<AnvilSlot, BukkitSlot> slots, Component defaultTitle, Set<CloseListener> closeListeners, Set<MovingItemListener> movingItemListeners) {
        this.plugin = plugin;
        this.initialization = new HashMap<>(slots);
        this.defaultTitle = defaultTitle;
        this.closeListeners = closeListeners;
        this.movingItemListeners = movingItemListeners;
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

    @Override
    public @NotNull Map<AnvilSlot, Slot> getInitialization() {
        return new HashMap<>(this.initialization);
    }

    @Override
    public @NotNull AnvilGuiRenderer prepare(@NotNull GuiAudience audience) {
        return new BukkitAnvilGuiRenderer(this.plugin, this, this.initialization, this.defaultTitle, audience, null);
    }

}
