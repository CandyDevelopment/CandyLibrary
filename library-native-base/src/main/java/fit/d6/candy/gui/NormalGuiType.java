package fit.d6.candy.gui;

import fit.d6.candy.api.gui.GuiType;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiInitializer;
import fit.d6.candy.exception.GuiException;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class NormalGuiType implements GuiType<NormalGui, NormalGuiInitializer> {

    private final int lines;

    public NormalGuiType(int lines) {
        if (lines > 6 || lines < 1)
            throw new GuiException("Normal gui can only have lines within [1, 6]");
        this.lines = lines;
    }

    @Override
    public @NotNull NormalGuiInitializer create(@NotNull Plugin plugin) {
        return new BukkitNormalGuiInitializer(plugin, this.lines);
    }

}
