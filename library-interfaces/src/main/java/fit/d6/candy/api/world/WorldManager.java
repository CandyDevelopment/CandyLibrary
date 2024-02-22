package fit.d6.candy.api.world;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public interface WorldManager {

    @NotNull
    Environment getEnvironmentOverworld();

    @NotNull
    Environment getEnvironmentNether();

    @NotNull
    Environment getEnvironmentTheEnd();

    @NotNull
    EnvironmentBuilder environmentBuilder(@NotNull String name);

    @NotNull
    EnvironmentBuilder environmentBuilder(@NotNull NamespacedKey key);

    @NotNull
    EnvironmentBuilder copyEnvironmentBuilder(@NotNull Environment environment, @NotNull String name);

    @NotNull
    EnvironmentBuilder copyEnvironmentBuilder(@NotNull Environment environment, @NotNull NamespacedKey key);

    @NotNull
    Environment registerEnvironment(@NotNull EnvironmentBuilder builder);

    @NotNull
    FlatSettings flatSettings();

    @NotNull
    WorldInitializer create(@NotNull String name);

    @NotNull
    WorldInitializer create(@NotNull NamespacedKey key);

    @NotNull
    static WorldManager getManager() {
        return WorldService.getService().getWorldManager();
    }

}
