package fit.d6.candy.api.world;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface WorldService extends Service {

    @NotNull
    WorldManager getWorldManager();

    @NotNull
    static WorldService getService() {
        return CandyLibrary.getLibrary().getService(WorldService.class);
    }

}
