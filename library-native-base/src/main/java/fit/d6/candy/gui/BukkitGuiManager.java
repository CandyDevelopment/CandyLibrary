package fit.d6.candy.gui;

import fit.d6.candy.api.gui.GuiManager;
import fit.d6.candy.api.gui.GuiType;
import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiInitializer;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiInitializer;
import fit.d6.candy.exception.GuiException;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class BukkitGuiManager implements GuiManager, Listener {

    @Override
    public @NotNull GuiType<NormalGui, NormalGuiInitializer> normal(int lines) {

        return switch (lines) {
            case 1 -> BukkitGuiTypes.NORMAL_1X9;
            case 2 -> BukkitGuiTypes.NORMAL_2X9;
            case 3 -> BukkitGuiTypes.NORMAL_3X9;
            case 4 -> BukkitGuiTypes.NORMAL_4X9;
            case 5 -> BukkitGuiTypes.NORMAL_5X9;
            case 6 -> BukkitGuiTypes.NORMAL_6X9;
            default -> throw new GuiException("Normal gui can only have lines within [1, 6]");
        };
    }

    @Override
    public @NotNull GuiType<AnvilGui, AnvilGuiInitializer> anvil() {
        return AnvilGuiType.INSTANCE;
    }


}
