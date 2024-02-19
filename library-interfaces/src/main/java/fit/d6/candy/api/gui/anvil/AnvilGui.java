package fit.d6.candy.api.gui.anvil;

import fit.d6.candy.api.gui.Gui;
import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface AnvilGui extends Gui {

    @NotNull
    Map<AnvilSlot, Slot> getInitialization();

    @NotNull
    AnvilGuiRenderer prepare(@NotNull GuiAudience audience);

}
