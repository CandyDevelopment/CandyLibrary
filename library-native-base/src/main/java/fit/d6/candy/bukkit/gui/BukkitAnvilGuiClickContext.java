package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.anvil.AnvilGuiClickContext;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

public class BukkitAnvilGuiClickContext extends BukkitAnvilGuiContext implements AnvilGuiClickContext {

    private final BukkitAnvilGuiScene scene;
    private final InventoryAction action;
    private final ClickType type;

    public BukkitAnvilGuiClickContext(GuiAudience audience, BukkitAnvilGuiScene scene, InventoryAction action, ClickType type) {
        super(audience, scene);
        this.scene = scene;
        this.action = action;
        this.type = type;
    }

    @Override
    public @NotNull InventoryAction getClickAction() {
        return this.action;
    }

    @Override
    public @NotNull ClickType getClickType() {
        return this.type;
    }

    @Override
    public @NotNull String getText() {
        return this.scene.fakeAnvil.getItemName();
    }

}
