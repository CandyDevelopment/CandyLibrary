package fit.d6.candy.api.world;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public interface WorldManager {

    @NotNull
    WorldInitializer create(@NotNull String name);

    @NotNull
    WorldInitializer create(@NotNull NamespacedKey key);

    @NotNull
    static WorldManager getManager() {
        return WorldService.getService().getWorldManager();
    }

}
