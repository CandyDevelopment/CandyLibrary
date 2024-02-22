package fit.d6.candy.world;

import fit.d6.candy.api.world.Environment;
import fit.d6.candy.api.world.FlatSettings;
import fit.d6.candy.api.world.WorldInitializer;
import fit.d6.candy.exception.WorldException;
import net.kyori.adventure.util.TriState;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BukkitWorldInitializer implements WorldInitializer {

    private final WorldCreator creator;
    private Environment environment;

    public BukkitWorldInitializer(NamespacedKey name) {
        this.creator = new WorldCreator(name);
    }

    public BukkitWorldInitializer(WorldCreator creator) {
        this.creator = creator;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public @NotNull WorldInitializer seed(long seed) {
        this.creator.seed(seed);
        return this;
    }

    @Override
    public @NotNull WorldInitializer environment(World.@NotNull Environment environment) {
        this.creator.environment(environment);
        switch (environment) {
            case NORMAL -> this.environment(BukkitEnvironment.OVERWORLD);
            case NETHER -> this.environment(BukkitEnvironment.NETHER);
            case THE_END -> this.environment(BukkitEnvironment.THE_END);
            case CUSTOM -> throw new WorldException("Illegal world environment");
        }
        return this;
    }

    @Override
    public @NotNull WorldInitializer environment(@NotNull Environment environment) {
        this.environment = environment;
        return this;
    }

    @Override
    public @NotNull WorldInitializer type(@NotNull WorldType type) {
        this.creator.type(type);
        return this;
    }

    @Override
    public @NotNull WorldInitializer generator(@NotNull ChunkGenerator generator) {
        this.creator.generator(generator);
        return this;
    }

    @Override
    public @NotNull WorldInitializer generator(@NotNull String generator) {
        this.creator.generator(generator);
        return this;
    }

    @Override
    public @NotNull WorldInitializer biomeProvider(@NotNull BiomeProvider provider) {
        this.creator.biomeProvider(provider);
        return this;
    }

    @Override
    public @NotNull WorldInitializer generatorsSettings(@NotNull String generatorsSettings) {
        this.creator.generatorSettings(generatorsSettings);
        return this;
    }

    @Override
    public @NotNull WorldInitializer generatorsSettings(@NotNull FlatSettings flatSettings) {
        return this.generatorsSettings(flatSettings.toString());
    }

    @Override
    public @NotNull WorldInitializer generateStructures(boolean generate) {
        this.creator.generateStructures(generate);
        return this;
    }

    @Override
    public @NotNull WorldInitializer hardcore(boolean hardcore) {
        this.creator.hardcore(hardcore);
        return this;
    }

    @Override
    public @NotNull WorldInitializer keepSpawnLoaded(@NotNull TriState state) {
        this.creator.keepSpawnLoaded(state);
        return this;
    }

    @Override
    public void initialize(@NotNull Plugin plugin, @NotNull Consumer<fit.d6.candy.api.world.@NotNull World> consumer) {
        Creator.getCreator().create(plugin, this.creator, this, consumer);
    }

}
