package fit.d6.candy.world;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fit.d6.candy.api.world.FlatSettings;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BukkitFlatSettings implements FlatSettings {

    private final List<Map.Entry<Material, Integer>> layers = new ArrayList<>();
    private Biome biome;
    private Boolean lakes;
    private Boolean features;
    private Set<Structure> structures;

    @Override
    public @NotNull FlatSettings layer(@NotNull Material type, int height) {
        return this;
    }

    @Override
    public @NotNull FlatSettings biome(@NotNull Biome biome) {
        return this;
    }

    @Override
    public @NotNull FlatSettings lakes(boolean lakes) {
        return this;
    }

    @Override
    public @NotNull FlatSettings features(boolean features) {
        return this;
    }

    @Override
    public @NotNull FlatSettings structures(@NotNull Set<@NotNull Structure> structures) {
        return this;
    }

    @Override
    public String toString() {
        JsonObject jsonObject = new JsonObject();

        JsonArray layers = new JsonArray();
        for (Map.Entry<Material, Integer> entry : this.layers) {
            JsonObject layer = new JsonObject();
            layer.addProperty("block", entry.getKey().getKey().getKey());
            layer.addProperty("height", entry.getValue());
            layers.add(layer);
        }
        jsonObject.add("layers", layers);
        if (this.biome != null)
            jsonObject.addProperty("biome", this.biome.getKey().getKey());
        if (this.lakes != null)
            jsonObject.addProperty("lakes", this.lakes);
        if (this.features != null)
            jsonObject.addProperty("features", this.features);
        if (this.structures != null) {
            JsonArray structures = new JsonArray();
            this.structures.stream()
                    .map(Keyed::getKey)
                    .map(NamespacedKey::getKey)
                    .forEach(structures::add);
            jsonObject.add("structure_overrides", structures);
        }
        return jsonObject.toString();
    }

}
