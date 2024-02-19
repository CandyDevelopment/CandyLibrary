package fit.d6.candy.nms.v1_19_R3.nbt;

import fit.d6.candy.api.nbt.NbtShort;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.ShortTag;
import org.jetbrains.annotations.NotNull;

public class BukkitNbtShort extends BukkitNbtNumber implements NbtShort {

    public BukkitNbtShort(ShortTag nms) {
        super(nms);
    }

    @Override
    public @NotNull NbtType getType() {
        return NbtType.SHORT;
    }

    @Override
    public @NotNull NbtShort copy() {
        return new BukkitNbtShort((ShortTag) this.getNms().copy());
    }

}
