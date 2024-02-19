package fit.d6.candy.api.gui;

import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiInitializer;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiInitializer;
import org.jetbrains.annotations.NotNull;

public interface GuiManager {

    @NotNull
    GuiType<NormalGui, NormalGuiInitializer> normal(int lines);

    @NotNull
    GuiType<AnvilGui, AnvilGuiInitializer> anvil();


}
