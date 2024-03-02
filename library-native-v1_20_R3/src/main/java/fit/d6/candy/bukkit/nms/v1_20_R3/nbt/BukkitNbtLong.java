package fit.d6.candy.bukkit.nms.v1_20_R3.nbt;

import fit.d6.candy.api.nbt.NbtLong;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.LongTag;
import org.jetbrains.annotations.NotNull;

public class BukkitNbtLong extends BukkitNbtNumber implements NbtLong {

    public BukkitNbtLong(LongTag nms) {
        super(nms);
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
