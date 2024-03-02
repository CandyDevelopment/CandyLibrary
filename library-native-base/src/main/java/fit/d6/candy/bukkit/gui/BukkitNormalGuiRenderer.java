package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiRenderer;
import fit.d6.candy.api.gui.normal.NormalGuiScene;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import fit.d6.candy.bukkit.exception.GuiException;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BukkitNormalGuiRenderer extends BukkitGuiRenderer<NormalGui, NormalGuiRenderer, NormalGuiScene> implements NormalGuiRenderer {

    private final Plugin plugin;
    private final BukkitNormalGui gui;
    private final int lines;
    private final Map<Integer, BukkitSlot> initialization;
    private Component title;
    private final GuiAudience audience;
    private final BukkitNormalGuiScene previous;

    public BukkitNormalGuiRenderer(Plugin plugin, BukkitNormalGui gui, int lines, Map<Integer, BukkitSlot> initialization, Component defaultTitle, GuiAudience audience, BukkitNormalGuiScene previous) {
        this.plugin = plugin;
        this.gui = gui;
        this.lines = lines;
        this.title = defaultTitle;
        this.initialization = new HashMap<>(initialization);
        this.audience = audience;
        this.previous = previous;
    }

    @Override
    public boolean hasSlot(int i) {
        return this.initialization.containsKey(i);
    }

    @Override
    public @NotNull NormalGuiRenderer slot(int i, @NotNull Consumer<SlotBuilder> consumer) {
        BukkitSlotBuilder builder = new BukkitSlotBuilder();
        consumer.accept(builder);
        this.initialization.put(i, builder.build());
        return this;
    }

    @Override
    public @NotNull NormalGuiScene render() {
        if (this.previous != null) {
            if (previous.isDropped())
                throw new GuiException("Trying to refresh a dropped scene");
            this.previous.update(this, this.initialization, this.title);
            return this.previous;
        } else {
            return new BukkitNormalGuiScene(this.plugin, this.gui, this.lines, this.initialization, this.title, this.audience, this);
        }
    }

    @Override
    public boolean isFull() {
        return this.initialization.size() == this.lines * 9;
    }

    @Override
    public boolean isEmpty() {
        return this.initialization.isEmpty();
    }

    @Override
    public void addSlot(@NotNull Consumer<SlotBuilder> consumer) {
        if (this.isFull())
            throw new GuiException("The gui is full");
        int slot = 0;
        for (int i = 0; i < this.lines * 9; i++) {
            if (this.initialization.containsKey(i))
                continue;
            slot = i;
            break;
        }
        this.slot(slot, consumer);
    }

    @Override
    public @NotNull NormalGuiRenderer title(Component component) {
        this.title = component;
        return this;
    }
}
