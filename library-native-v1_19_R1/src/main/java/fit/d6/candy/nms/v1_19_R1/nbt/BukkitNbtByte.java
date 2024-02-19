package fit.d6.candy.nms.v1_19_R1.nbt;

import fit.d6.candy.api.nbt.NbtByte;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.ByteTag;
import org.jetbrains.annotations.NotNull;

public class BukkitNbtByte extends BukkitNbtNumber implements NbtByte {

    public BukkitNbtByte(ByteTag nms) {
        super(nms);
    }

    @Override
    public int getSizeInBytes() {
        return 9;
    }

    @Override
    public @NotNull NbtType getType() {
        return NbtType.BYTE;
    }

    @Override
    public @NotNull NbtByte copy() {
        return new BukkitNbtByte((ByteTag) this.getNms().copy());
    }

}
