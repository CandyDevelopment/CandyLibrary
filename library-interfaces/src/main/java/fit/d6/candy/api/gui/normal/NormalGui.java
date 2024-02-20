package fit.d6.candy.api.gui.normal;

import fit.d6.candy.api.gui.Gui;
import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.GuiService;
import fit.d6.candy.api.gui.slot.Slot;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface NormalGui extends Gui {

    @NotNull
    Map<Integer, Slot> getInitialization();

    @NotNull
    NormalGuiRenderer prepare(@NotNull GuiAudience audience);

    @NotNull
    static NormalGuiInitializer create(@NotNull Plugin plugin, int lines) {
        return GuiService.getService().getGuiManager().normal(lines).create(plugin);
    }

}
