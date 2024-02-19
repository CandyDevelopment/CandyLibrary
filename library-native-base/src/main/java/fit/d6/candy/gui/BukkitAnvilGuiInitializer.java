package fit.d6.candy.gui;

import fit.d6.candy.api.gui.CloseListener;
import fit.d6.candy.api.gui.MovingItemListener;
import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiInitializer;
import fit.d6.candy.api.gui.anvil.AnvilSlot;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import fit.d6.candy.exception.GuiException;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class BukkitAnvilGuiInitializer extends BukkitGuiInitializer<AnvilGui, AnvilGuiInitializer> implements AnvilGuiInitializer {

    private final Map<AnvilSlot, BukkitSlot> slots = new HashMap<>();

    private final Plugin plugin;
    private Component defaultTitle;

    private final Set<CloseListener> closeListeners = new HashSet<>();
    private final Set<MovingItemListener> movingItemListeners = new HashSet<>();

    public BukkitAnvilGuiInitializer(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull AnvilGuiInitializer title(Component component) {
        this.defaultTitle = component;
        return this;
    }

    @Override
    public @NotNull AnvilGuiInitializer closeListener(@NotNull CloseListener listener) {
        this.closeListeners.add(listener);
        return this;
    }

    @Override
    public @NotNull AnvilGuiInitializer movingItemListener(@NotNull MovingItemListener listener) {
        this.movingItemListeners.add(listener);
        return this;
    }

    @Override
    public @NotNull AnvilGui initialize() {
        return new BukkitAnvilGui(this.plugin, this.slots, this.defaultTitle, this.closeListeners, this.movingItemListeners);
    }

    @Override
    public @NotNull AnvilGuiInitializer slot(@NotNull AnvilSlot slot, @NotNull Consumer<SlotBuilder> consumer) {
        if (slot == null)
            throw new GuiException("Slot cannot be null");
        BukkitSlotBuilder builder = new BukkitSlotBuilder();
        consumer.accept(builder);
        this.slots.put(slot, builder.build());
        return this;
    }

}
