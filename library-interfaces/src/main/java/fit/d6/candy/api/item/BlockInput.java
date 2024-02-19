package fit.d6.candy.api.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public interface BlockInput {

    @NotNull
    Material getType();

    @NotNull
    BlockData getBlockData();

    /**
     * Place the block into the world
     *
     * @param location where to place
     * @param flags    default is 2, its function is unknown
     * @return placed block
     */
    @NotNull
    Block place(@NotNull Location location, int flags);

}
