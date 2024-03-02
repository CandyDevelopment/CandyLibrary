package fit.d6.candy.bukkit.world;

import fit.d6.candy.api.world.EnvironmentBuilder;
import fit.d6.candy.api.world.NoiseSettings;
import fit.d6.candy.bukkit.exception.WorldException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitEnvironmentBuilder implements EnvironmentBuilder {

    private final NamespacedKey key;

    public World.Environment base;
    public NoiseSettings noiseSettings;
    public Long fixedTime = null;
    public boolean hasSkylight = true;
    public boolean hasCeiling = true;
    public boolean ultraWarm = false;
    public boolean natural = true;
    public double coordinateScale = 1.0;

    public boolean enderDragonFight = false;

    public boolean piglinSafe = false;
    public boolean bedWorks = true;
    public boolean respawnAnchorWorks = false;
    public boolean hasRaids = true;
    public int minY = -64;
    public int height = 384;
    public int logicalHeight = 384;
    public Tag<Material> infiniburn = Tag.INFINIBURN_OVERWORLD;
    public NamespacedKey effectsLocation = NamespacedKey.minecraft("overworld");
    public float ambientLight = 0.0f;

    public int monsterSpawnLightTest = 7;
    public int monsterSpawnBlockLightLimit = 0;

    public BukkitEnvironmentBuilder(NamespacedKey key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public @NotNull EnvironmentBuilder base(World.@NotNull Environment environment) {
        if (environment == World.Environment.CUSTOM)
            throw new WorldException("Illegal world environment");
        this.base = environment;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder noiseSettings(@NotNull NoiseSettings noiseSettings) {
        this.noiseSettings = noiseSettings;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder ambientLight(@NotNull Float ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder bedWorks(boolean bedWorks) {
        this.bedWorks = bedWorks;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder coordinateScale(double coordinateScale) {
        this.coordinateScale = coordinateScale;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder effectsLocation(@NotNull NamespacedKey effectsLocation) {
        this.effectsLocation = effectsLocation;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder fixedTime(@Nullable Long fixedTime) {
        this.fixedTime = fixedTime;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder ceiling(boolean hasCeiling) {
        this.hasCeiling = hasCeiling;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder raids(boolean hasRaids) {
        this.hasRaids = hasRaids;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder skylight(boolean hasSkylight) {
        this.hasSkylight = hasSkylight;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder infiniburn(Tag<Material> infiniburn) {
        this.infiniburn = infiniburn;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder natural(boolean natural) {
        this.natural = natural;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder enderDragonFight(boolean enderDragonFight) {
        this.enderDragonFight = enderDragonFight;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder piglinSafe(boolean piglinSafe) {
        this.piglinSafe = piglinSafe;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder respawnAnchorWorks(boolean respawnAnchorWorks) {
        this.respawnAnchorWorks = respawnAnchorWorks;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder ultraWarm(boolean ultraWarm) {
        this.ultraWarm = ultraWarm;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder monsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit) {
        this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder monsterSpawnLightTest(int monsterSpawnLightTest) {
        this.monsterSpawnLightTest = monsterSpawnLightTest;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder minY(int minY) {
        if (minY > 2016 || minY < -2032 || minY % 16 != 0)
            throw new WorldException("The minY(" + minY + ") is below -2032 or higher then 2016 or not a multiple of 16!");
        this.minY = minY;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder height(int height) {
        if (height > 4064 || height < 16 || height % 16 != 0)
            throw new WorldException("The height(" + height + ") is below 16 or not a multiple of 16 or is greater 4064");
        this.height = height;
        return this;
    }

    @Override
    public @NotNull EnvironmentBuilder logicalHeight(int logicalHeight) {
        this.logicalHeight = logicalHeight;
        return this;
    }

}
