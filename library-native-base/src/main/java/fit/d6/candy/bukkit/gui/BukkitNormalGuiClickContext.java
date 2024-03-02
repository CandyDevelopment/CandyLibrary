package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.normal.NormalGuiClickContext;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

public class BukkitNormalGuiClickContext extends BukkitNormalGuiContext implements NormalGuiClickContext {

    private final InventoryAction action;
    private final ClickType type;

    public BukkitNormalGuiClickContext(BukkitNormalGuiScene scene, GuiAudience audience, InventoryAction action, ClickType type) {
        super(scene, audience);
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

}
