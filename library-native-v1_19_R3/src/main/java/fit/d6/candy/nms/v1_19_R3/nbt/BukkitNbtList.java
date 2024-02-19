package fit.d6.candy.nms.v1_19_R3.nbt;

import fit.d6.candy.api.nbt.NbtBase;
import fit.d6.candy.api.nbt.NbtCompound;
import fit.d6.candy.api.nbt.NbtList;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class BukkitNbtList extends BukkitNbtBase implements NbtList {

    private final ListTag tag;

    public BukkitNbtList(ListTag nms) {
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
        return NbtType.LIST;
    }

    @Override
    public @NotNull String getAsString() {
        return this.tag.getAsString();
    }

    @Override
    public NbtBase remove(int index) {
        Tag tag = this.tag.remove(index);
        return tag == null ? null : BukkitNbtCompound.nmsToCandy(tag);
    }

    @Override
    public boolean isEmpty() {
        return this.tag.isEmpty();
    }

    @Override
    public @NotNull NbtCompound getCompound(int index) {
        return new BukkitNbtCompound(this.tag.getCompound(index));
    }

    @Override
    public @NotNull NbtList getList(int index) {
        return new BukkitNbtList(this.tag.getList(index));
    }

    @Override
    public short getShort(int index) {
        return this.tag.getShort(index);
    }

    @Override
    public int getInt(int index) {
        return this.tag.getInt(index);
    }

    @Override
    public int[] getIntArray(int index) {
        return this.tag.getIntArray(index);
    }

    @Override
    public long[] getLongArray(int index) {
        return this.tag.getLongArray(index);
    }

    @Override
    public double getDouble(int index) {
        return this.tag.getDouble(index);
    }

    @Override
    public float getFloat(int index) {
        return this.tag.getFloat(index);
    }

    @Override
    public @NotNull String getString(int index) {
        return this.tag.getString(index);
    }

    @Override
    public int size() {
        return this.tag.size();
    }

    @Override
    public NbtBase get(int index) {
        Tag tag = this.tag.get(index);
        return tag == null ? null : BukkitNbtCompound.nmsToCandy(tag);
    }

    @Override
    public boolean set(int index, @NotNull NbtBase element) {
        return this.tag.setTag(index, ((BukkitNbtBase) element).getNms());
    }

    @Override
    public boolean add(int index, @NotNull NbtBase element) {
        return this.tag.addTag(index, ((BukkitNbtBase) element).getNms());
    }

    @Override
    public boolean add(@NotNull NbtBase element) {
        return this.tag.add(((BukkitNbtBase) element).getNms());
    }

    @Override
    public void clear() {
        this.tag.clear();
    }

    @Override
    public @NotNull NbtType getElementType() {
        return NbtType.getById(this.tag.getElementType());
    }

    @Override
    public @NotNull NbtList copy() {
        return new BukkitNbtList(this.tag.copy());
    }

    @Override
    public Tag getNms() {
        return this.tag;
    }

}
