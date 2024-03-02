package fit.d6.candy.bukkit.nms.v1_20_R2.item;

import fit.d6.candy.api.item.BlockInput;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.jetbrains.annotations.NotNull;

public class BukkitBlockInput implements BlockInput {

    private final net.minecraft.commands.arguments.blocks.BlockInput nmsInput;

    public BukkitBlockInput(net.minecraft.commands.arguments.blocks.BlockInput nms) {
        this.nmsInput = nms;
    }

    @Override
    public @NotNull Material getType() {
        return this.nmsInput.getState().getBukkitMaterial();
    }

    @Override
    public @NotNull BlockData getBlockData() {
        return CraftBlockData.fromData(this.nmsInput.getState());
    }

    @Override
    public @NotNull Block place(@NotNull Location location, int flags) {
        this.nmsInput.place(((CraftWorld) location.getWorld()).getHandle(), CraftLocation.toBlockPosition(location), flags);
        return location.getBlock();
    }

}
