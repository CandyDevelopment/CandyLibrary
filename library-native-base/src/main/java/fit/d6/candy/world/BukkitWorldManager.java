package fit.d6.candy.world;

import fit.d6.candy.api.world.WorldInitializer;
import fit.d6.candy.api.world.WorldManager;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class BukkitWorldManager implements WorldManager {

    @Override
    public @NotNull WorldInitializer create(@NotNull String name) {
        return this.create(NamespacedKey.minecraft(name));
    }

    @Override
    public @NotNull WorldInitializer create(@NotNull NamespacedKey key) {
        return new BukkitWorldInitializer(key);
    }

}
