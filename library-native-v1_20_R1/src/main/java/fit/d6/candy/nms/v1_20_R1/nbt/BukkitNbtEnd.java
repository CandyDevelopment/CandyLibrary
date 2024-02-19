package fit.d6.candy.nms.v1_20_R1.nbt;

import fit.d6.candy.api.nbt.NbtBase;
import fit.d6.candy.api.nbt.NbtEnd;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class BukkitNbtEnd extends BukkitNbtBase implements NbtEnd {

    public final static BukkitNbtEnd INSTANCE = new BukkitNbtEnd(EndTag.INSTANCE);

    private final EndTag tag;

    private BukkitNbtEnd(EndTag nms) {
        this.tag = nms;
    }

    @Override
    public void write(@NotNull DataOutput output) {
        try {
            this.tag.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSizeInBytes() {
        return this.tag.sizeInBytes();
    }

    @Override
    public byte getId() {
        return (byte) this.getType().getId();
    }

    @Override
    public @NotNull NbtType getType() {
        return NbtType.END;
    }

    @Override
    public @NotNull NbtBase copy() {
        return this;
    }

    @Override
    public @NotNull String getAsString() {
        return this.tag.getAsString();
    }

    @Override
    public Tag getNms() {
        return this.tag;
    }

}
