package fit.d6.candy.api.gui.anvil;

import fit.d6.candy.api.gui.Gui;
import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.GuiService;
import fit.d6.candy.api.gui.slot.Slot;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface AnvilGui extends Gui {

    @NotNull
    Map<AnvilSlot, Slot> getInitialization();

    @NotNull
    AnvilGuiRenderer prepare(@NotNull GuiAudience audience);

    @NotNull
    static AnvilGuiInitializer create(@NotNull Plugin plugin) {
        return GuiService.getService().getGuiManager().anvil().create(plugin);
    }

}
