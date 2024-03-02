package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.CloseListener;
import fit.d6.candy.api.gui.MovingItemListener;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiInitializer;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class BukkitNormalGuiInitializer extends BukkitGuiInitializer<NormalGui, NormalGuiInitializer> implements NormalGuiInitializer {

    private final int lines;
    private final Map<Integer, BukkitSlot> slots = new HashMap<>();

    private final Plugin plugin;
    private Component defaultTitle;

    private final Set<CloseListener> closeListeners = new HashSet<>();
    private final Set<MovingItemListener> movingItemListeners = new HashSet<>();

    public BukkitNormalGuiInitializer(Plugin plugin, int lines) {
        this.plugin = plugin;
        this.lines = lines;
    }

    @Override
    public @NotNull NormalGuiInitializer slot(int i, @NotNull Consumer<SlotBuilder> consumer) {
        BukkitSlotBuilder builder = new BukkitSlotBuilder();
        consumer.accept(builder);
        this.slots.put(i, builder.build());
        return this;
    }

    @Override
    public @NotNull NormalGuiInitializer title(Component component) {
        this.defaultTitle = component;
        return this;
    }

    @Override
    public @NotNull NormalGuiInitializer closeListener(@NotNull CloseListener listener) {
        this.closeListeners.add(listener);
        return this;
    }

    @Override
    public @NotNull NormalGuiInitializer movingItemListener(@NotNull MovingItemListener listener) {
        this.movingItemListeners.add(listener);
        return this;
    }

    @Override
    public @NotNull NormalGui initialize() {
        return new BukkitNormalGui(this.plugin, this.lines, this.slots, this.defaultTitle, this.closeListeners, this.movingItemListeners);
    }

}