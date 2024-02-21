package fit.d6.candy.api.gui;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import fit.d6.candy.api.annotation.FoliaSupport;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@FoliaSupport
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
