package fit.d6.candy.bukkit.nms.v1_17_R1.nbt;

import fit.d6.candy.api.nbt.NbtLong;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.LongTag;
import org.jetbrains.annotations.NotNull;

public class BukkitNbtLong extends BukkitNbtNumber implements NbtLong {

    public BukkitNbtLong(LongTag nms) {
        super(nms);
    }

    @Override
    public int getSizeInBytes() {
        return 16;
    }

    @Override
    public @NotNull NbtType getType() {
        return NbtType.LONG;
    }

    @Override
    public @NotNull NbtLong copy() {
        return new BukkitNbtLong((LongTag) this.getNms().copy());
    }

}
