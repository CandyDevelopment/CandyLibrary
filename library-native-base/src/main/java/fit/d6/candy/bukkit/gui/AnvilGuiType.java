package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiType;
import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiInitializer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class AnvilGuiType implements GuiType<AnvilGui, AnvilGuiInitializer> {

    public final static AnvilGuiType INSTANCE = new AnvilGuiType();

    private AnvilGuiType() {
    }

    @Override
    public @NotNull AnvilGuiInitializer create(@NotNull Plugin plugin) {
        return new BukkitAnvilGuiInitializer(plugin);
    }

}
