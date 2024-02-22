package fit.d6.candy.api.world;

import net.kyori.adventure.util.TriState;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface WorldInitializer {

    @NotNull
    WorldInitializer seed(long seed);

    @NotNull
    WorldInitializer environment(@NotNull World.Environment environment);

    @NotNull
    WorldInitializer environment(@NotNull Environment environment);

    @NotNull
    WorldInitializer type(@NotNull WorldType type);

    @NotNull
    WorldInitializer generator(@NotNull ChunkGenerator generator);

    @NotNull
    WorldInitializer generator(@NotNull String generator);

    @NotNull
    WorldInitializer biomeProvider(@NotNull BiomeProvider provider);

    @NotNull
    WorldInitializer generatorsSettings(@NotNull String generatorsSettings);

    @NotNull
    WorldInitializer generatorsSettings(@NotNull FlatSettings flatSettings);

    @NotNull
    WorldInitializer generateStructures(boolean generate);

    @NotNull
    WorldInitializer hardcore(boolean hardcore);

    @NotNull
    WorldInitializer keepSpawnLoaded(@NotNull TriState state);

    void initialize(@NotNull Plugin plugin, @NotNull Consumer<fit.d6.candy.api.world.@NotNull World> consumer);

}
