package fit.d6.candy.world;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.world.WorldManager;
import fit.d6.candy.api.world.WorldService;
import org.jetbrains.annotations.NotNull;

public class BukkitWorldService extends BukkitService implements WorldService {

    private final BukkitWorldManager worldManager = new BukkitWorldManager();

    @Override
    public @NotNull String getId() {
        return "world";
    }

    @Override
    public @NotNull WorldManager getWorldManager() {
        return this.worldManager;
    }

}
