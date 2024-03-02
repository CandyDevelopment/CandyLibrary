package fit.d6.candy.bukkit.world;

import fit.d6.candy.api.world.World;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class BukkitWorld implements World {

    private final org.bukkit.World world;

    public BukkitWorld(org.bukkit.World world) {
        this.world = world;
    }

    @NotNull
    @Override
    public org.bukkit.World asBukkit() {
        return this.world;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof World))
            return false;
        return this.world.equals(((World) obj).asBukkit());
    }

    @Override
    public int hashCode() {
        int sum = 0;
        for (byte b : "candy".getBytes(StandardCharsets.UTF_8))
            sum += b;
        return sum + this.world.hashCode();
    }

}
