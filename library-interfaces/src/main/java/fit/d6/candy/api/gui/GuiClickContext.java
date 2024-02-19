package fit.d6.candy.api.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

public interface GuiClickContext<G extends Gui, GR extends GuiRenderer<G, GR, GS>, GS extends GuiScene<G, GR, GS>> extends GuiContext<G, GR, GS> {

    @NotNull
    InventoryAction getClickAction();

    @NotNull
    ClickType getClickType();

}
