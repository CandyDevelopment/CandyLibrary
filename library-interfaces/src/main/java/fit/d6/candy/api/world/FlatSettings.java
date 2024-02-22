package fit.d6.candy.api.world;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface FlatSettings {

    @NotNull
    FlatSettings layer(@NotNull Material type, int height);

    @NotNull
    FlatSettings biome(@NotNull Biome biome);

    @NotNull
    FlatSettings lakes(boolean lakes);

    @NotNull
    FlatSettings features(boolean features);

    @NotNull
    FlatSettings structures(@NotNull Set<@NotNull Structure> structures);

    @NotNull
    static FlatSettings of() {
        return WorldManager.getManager().flatSettings();
    }

}
