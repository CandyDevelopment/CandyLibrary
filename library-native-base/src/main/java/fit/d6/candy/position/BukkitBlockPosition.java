package fit.d6.candy.position;

import fit.d6.candy.api.position.BlockPosition;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class BukkitBlockPosition implements BlockPosition {

    private final int x;
    private final int y;
    private final int z;

    public BukkitBlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    @Override
    public @NotNull Location toLocation(@NotNull World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    public static BlockPosition fromBukkit(Location location) {
        return new BukkitBlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

}
