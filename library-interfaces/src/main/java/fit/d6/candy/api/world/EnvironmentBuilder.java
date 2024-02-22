package fit.d6.candy.api.world;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EnvironmentBuilder {

    @NotNull
    EnvironmentBuilder base(@NotNull World.Environment environment);

    @NotNull
    EnvironmentBuilder noiseSettings(@NotNull NoiseSettings noiseSettings);

    @NotNull
    EnvironmentBuilder ambientLight(@NotNull Float ambientLight);

    @NotNull
    EnvironmentBuilder bedWorks(boolean bedWorks);

    @NotNull
    EnvironmentBuilder coordinateScale(double coordinateScale);

    @NotNull
    EnvironmentBuilder effectsLocation(@NotNull NamespacedKey effectsLocation);

    @NotNull
    EnvironmentBuilder fixedTime(@Nullable Long fixedTime);

    @NotNull
    EnvironmentBuilder ceiling(boolean hasCeiling);

    @NotNull
    EnvironmentBuilder raids(boolean hasRaids);

    @NotNull
    EnvironmentBuilder skylight(boolean hasSkylight);

    @NotNull
    EnvironmentBuilder infiniburn(Tag<Material> infiniburn);

    @NotNull
    EnvironmentBuilder natural(boolean natural);

    @NotNull
    EnvironmentBuilder enderDragonFight(boolean enderDragonFight);

    @NotNull
    EnvironmentBuilder piglinSafe(boolean piglinSafe);

    @NotNull
    EnvironmentBuilder respawnAnchorWorks(boolean respawnAnchorWorks);

    @NotNull
    EnvironmentBuilder ultraWarm(boolean ultraWarm);

    @NotNull
    EnvironmentBuilder monsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit);

    @NotNull
    EnvironmentBuilder monsterSpawnLightTest(int monsterSpawnLightTest);

    @NotNull
    EnvironmentBuilder minY(int minY);

    @NotNull
    EnvironmentBuilder height(int height);

    @NotNull
    EnvironmentBuilder logicalHeight(int logicalHeight);

    @NotNull
    static EnvironmentBuilder of(@NotNull String name) {
        return WorldManager.getManager().environmentBuilder(name);
    }

    @NotNull
    static EnvironmentBuilder of(@NotNull NamespacedKey key) {
        return WorldManager.getManager().environmentBuilder(key);
    }

    @NotNull
    static EnvironmentBuilder copy(@NotNull Environment environment, @NotNull String name) {
        return WorldManager.getManager().copyEnvironmentBuilder(environment, name);
    }

    @NotNull
    static EnvironmentBuilder copy(@NotNull Environment environment, @NotNull NamespacedKey key) {
        return WorldManager.getManager().copyEnvironmentBuilder(environment, key);
    }

}
