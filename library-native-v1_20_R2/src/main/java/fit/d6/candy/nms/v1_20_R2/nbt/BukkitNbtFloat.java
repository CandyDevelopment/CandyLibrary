package fit.d6.candy.nms.v1_20_R2.nbt;

import fit.d6.candy.api.nbt.NbtFloat;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.FloatTag;
import org.jetbrains.annotations.NotNull;

public class BukkitNbtFloat extends BukkitNbtNumber implements NbtFloat {

    public BukkitNbtFloat(FloatTag nms) {
        super(nms);
    }

    @Override
    public @NotNull NbtType getType() {
        return NbtType.FLOAT;
    }

    @Override
    public @NotNull NbtFloat copy() {
        return new BukkitNbtFloat((FloatTag) this.getNms().copy());
    }

}
