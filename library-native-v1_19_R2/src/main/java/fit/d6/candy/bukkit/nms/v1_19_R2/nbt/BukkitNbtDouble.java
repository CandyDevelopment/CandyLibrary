package fit.d6.candy.bukkit.nms.v1_19_R2.nbt;

import fit.d6.candy.api.nbt.NbtDouble;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.DoubleTag;
import org.jetbrains.annotations.NotNull;

public class BukkitNbtDouble extends BukkitNbtNumber implements NbtDouble {

    public BukkitNbtDouble(DoubleTag nms) {
        super(nms);
    }

    @Override
    public @NotNull NbtType getType() {
        return NbtType.DOUBLE;
    }

    @Override
    public @NotNull NbtDouble copy() {
        return new BukkitNbtDouble((DoubleTag) this.getNms().copy());
    }

}
