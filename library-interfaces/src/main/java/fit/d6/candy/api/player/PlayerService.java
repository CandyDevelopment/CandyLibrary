package fit.d6.candy.api.player;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface PlayerService extends Service {

    @NotNull
    PlayerManager getPlayerManager();

    @NotNull
    SkinManager getSkinManager();

    @NotNull
    static PlayerService getService() {
        return CandyLibrary.getLibrary().getService(PlayerService.class);
    }

}
