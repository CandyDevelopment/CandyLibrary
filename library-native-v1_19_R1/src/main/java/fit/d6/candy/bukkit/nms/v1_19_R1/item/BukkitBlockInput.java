package fit.d6.candy.bukkit.nms.v1_19_R1.item;

import fit.d6.candy.api.item.BlockInput;
import fit.d6.candy.bukkit.nms.v1_19_R1.NmsUtilsV1_19_R1;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.block.data.CraftBlockData;
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
        this.nmsInput.place(((CraftWorld) location.getWorld()).getHandle(), NmsUtilsV1_19_R1.toBlockPos(location), flags);
        return location.getBlock();
    }

}
