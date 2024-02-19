package fit.d6.candy.api.gui;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface GuiService extends Service {

    @NotNull
    GuiManager getGuiManager();

    @NotNull
    GuiAudience getAudience(@NotNull Player player);

    @NotNull
    static GuiService getService() {
        return CandyLibrary.getLibrary().getService(GuiService.class);
    }

}
