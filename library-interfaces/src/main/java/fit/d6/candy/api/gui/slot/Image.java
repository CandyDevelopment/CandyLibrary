package fit.d6.candy.api.gui.slot;

import fit.d6.candy.api.gui.GuiContext;
import fit.d6.candy.api.gui.item.ItemBuilder;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Image {

    void render(@NotNull GuiContext context, @NotNull ItemBuilder builder);

}
