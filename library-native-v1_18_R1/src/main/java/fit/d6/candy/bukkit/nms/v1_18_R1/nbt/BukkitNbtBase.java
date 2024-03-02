package fit.d6.candy.bukkit.nms.v1_18_R1.nbt;

import fit.d6.candy.api.nbt.NbtBase;
import net.minecraft.nbt.Tag;

public abstract class BukkitNbtBase implements NbtBase {

    public abstract Tag getNms();

    @Override
    public String toString() {
        return this.getAsString();
    }

}
