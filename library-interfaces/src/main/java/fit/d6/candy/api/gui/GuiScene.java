package fit.d6.candy.api.gui;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface GuiScene<G extends Gui, GR extends GuiRenderer<G, GR, GS>, GS extends GuiScene<G, GR, GS>> {

    @NotNull
    GuiAudience getAudience();

    @NotNull
    Inventory asBukkit();

    @NotNull
    GR refresh(boolean keepPrevious);

    @NotNull
    GR back();

    @NotNull
    GR forward();

    boolean hasPrevious();

    boolean hasNext();

    boolean isDropped();

}
