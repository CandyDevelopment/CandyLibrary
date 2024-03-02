package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiRenderer;
import fit.d6.candy.api.gui.anvil.AnvilGuiScene;
import fit.d6.candy.api.gui.anvil.AnvilSlot;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import fit.d6.candy.bukkit.exception.GuiException;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BukkitAnvilGuiRenderer extends BukkitGuiRenderer<AnvilGui, AnvilGuiRenderer, AnvilGuiScene> implements AnvilGuiRenderer {

    private final Plugin plugin;
    private final BukkitAnvilGui gui;
    private final Map<AnvilSlot, BukkitSlot> slots;
    private Component title;
    private final GuiAudience audience;
    private final BukkitAnvilGuiScene previous;

    public BukkitAnvilGuiRenderer(Plugin plugin, BukkitAnvilGui gui, Map<AnvilSlot, BukkitSlot> initialization, Component defaultTitle, GuiAudience audience, BukkitAnvilGuiScene previous) {
        this.plugin = plugin;
        this.gui = gui;
        this.slots = new HashMap<>(initialization);
        this.title = defaultTitle;
        this.audience = audience;
        this.previous = previous;
    }

    @Override
    public @NotNull AnvilGuiRenderer title(Component component) {
        this.title = component;
        return this;
    }

    @Override
    public boolean hasSlot(@NotNull AnvilSlot slot) {
        return this.slots.containsKey(slot);
    }

    @Override
    public boolean isFull() {
        return this.slots.size() == 3;
    }

    @Override
    public boolean isEmpty() {
        return this.slots.isEmpty();
    }

    @Override
    public void addSlot(@NotNull Consumer<SlotBuilder> consumer) {
        if (this.isFull())
            throw new GuiException("The gui is full");
        AnvilSlot slot = AnvilSlot.LEFT_INPUT;
        if (this.slots.containsKey(slot))
            slot = AnvilSlot.RIGHT_INPUT;
        if (this.slots.containsKey(slot))
            slot = AnvilSlot.RESULT;
        this.slot(slot, consumer);
    }

    @Override
    public @NotNull AnvilGuiRenderer slot(@NotNull AnvilSlot slot, @NotNull Consumer<SlotBuilder> consumer) {
        if (slot == null)
            throw new GuiException("Slot cannot be null");
        BukkitSlotBuilder builder = new BukkitSlotBuilder();
        consumer.accept(builder);
        this.slots.put(slot, builder.build());
        return this;
    }

    @Override
    public @NotNull AnvilGuiScene render() {
        if (this.previous != null) {
            if (previous.isDropped())
                throw new GuiException("Trying to refresh a dropped scene");
            this.previous.update(this, this.slots, this.title);
            return this.previous;
        } else {
            return new BukkitAnvilGuiScene(this.plugin, this.gui, this.slots, this.title, this.audience, this);
        }
    }

}
