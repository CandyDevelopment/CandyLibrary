package fit.d6.candy.api.gui.slot;

import fit.d6.candy.api.gui.GuiContext;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Clicker {

    void trigger(@NotNull GuiContext context);

}
