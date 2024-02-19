package fit.d6.candy.nms.v1_18_R2.nbt;

import fit.d6.candy.api.nbt.NbtBase;
import fit.d6.candy.api.nbt.NbtLong;
import fit.d6.candy.api.nbt.NbtLongArray;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class BukkitNbtLongArray extends BukkitNbtBase implements NbtLongArray {

    private final LongArrayTag tag;

    public BukkitNbtLongArray(LongArrayTag nms) {
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
        return 24 + 8 * this.tag.size();
    }

    @Override
    public byte getId() {
        return (byte) this.getType().getId();
    }

    @Override
    public @NotNull NbtType getType() {
        return NbtType.BYTE_ARRAY;
    }

    @Override
    public @NotNull NbtBase copy() {
        return new BukkitNbtLongArray(this.tag.copy());
    }

    @Override
    public @NotNull String getAsString() {
        return this.tag.getAsString();
    }


    @Override
    public NbtLong get(int index) {
        LongTag tag = this.tag.get(index);
        return tag == null ? null : new BukkitNbtLong(tag);
    }

    @Override
    public boolean add(int index, @NotNull NbtLong element) {
        return this.tag.addTag(index, ((BukkitNbtBase) element).getNms());
    }

    @Override
    public boolean set(int index, @NotNull NbtLong element) {
        return this.tag.setTag(index, ((BukkitNbtBase) element).getNms());
    }

    @Override
    public boolean add(@NotNull NbtLong element) {
        return this.tag.add((LongTag) ((BukkitNbtBase) element).getNms());
    }

    @Override
    public NbtLong remove(int index) {
        LongTag tag = this.tag.remove(index);
        return tag == null ? null : new BukkitNbtLong(tag);
    }

    @Override
    public int size() {
        return this.tag.size();
    }

    @Override
    public long[] getAsLongArray() {
        return this.tag.getAsLongArray();
    }

    @Override
    public void clear() {
        this.tag.clear();
    }

    @Override
    public Tag getNms() {
        return this.tag;
    }

}
