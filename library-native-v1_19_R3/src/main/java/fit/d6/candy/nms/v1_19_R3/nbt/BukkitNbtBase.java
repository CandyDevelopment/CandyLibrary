package fit.d6.candy.nms.v1_19_R3.nbt;

import fit.d6.candy.api.nbt.NbtBase;
import net.minecraft.nbt.Tag;

public abstract class BukkitNbtBase implements NbtBase {

    public abstract Tag getNms();

    @Override
    public String toString() {
        return this.getAsString();
    }

}
