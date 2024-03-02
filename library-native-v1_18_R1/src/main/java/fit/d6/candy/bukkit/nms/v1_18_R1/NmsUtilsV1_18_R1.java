package fit.d6.candy.bukkit.nms.v1_18_R1;

import net.minecraft.core.BlockPos;
import org.bukkit.Location;

public final class NmsUtilsV1_18_R1 {

    public static BlockPos toBlockPos(Location location) {
        return new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Location toBukkit(BlockPos blockPos) {
        return new Location(null, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    private NmsUtilsV1_18_R1() {

    }

}
