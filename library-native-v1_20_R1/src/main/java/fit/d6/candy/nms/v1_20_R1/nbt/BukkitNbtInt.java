package fit.d6.candy.nms.v1_20_R1.nbt;

import fit.d6.candy.api.nbt.NbtInt;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.IntTag;
import org.jetbrains.annotations.NotNull;

public class BukkitNbtInt extends BukkitNbtNumber implements NbtInt {

    public BukkitNbtInt(IntTag nms) {
        super(nms);
    }

    @Override
    public @NotNull NbtType getType() {
        return NbtType.INT;
    }

    @Override
    public @NotNull NbtInt copy() {
        return new BukkitNbtInt((IntTag) this.getNms().copy());
    }

}