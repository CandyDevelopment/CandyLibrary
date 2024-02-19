package fit.d6.candy.nms.v1_19_R1;

import net.minecraft.core.BlockPos;
import org.bukkit.Location;

public final class NmsUtilsV1_19_R1 {

    public static BlockPos toBlockPos(Location location) {
        return new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Location toBukkit(BlockPos blockPos) {
        return new Location(null, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    private NmsUtilsV1_19_R1() {

    }

}
