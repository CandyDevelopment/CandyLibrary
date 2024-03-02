package fit.d6.candy.bukkit.nms.v1_20_R1.nbt;

import fit.d6.candy.api.nbt.NbtBase;
import fit.d6.candy.api.nbt.NbtInt;
import fit.d6.candy.api.nbt.NbtIntArray;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class BukkitNbtIntArray extends BukkitNbtBase implements NbtIntArray {
    private final IntArrayTag tag;

    public BukkitNbtIntArray(IntArrayTag nms) {
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
        return NbtType.INT_ARRAY;
    }

    @Override
    public @NotNull NbtBase copy() {
        return new BukkitNbtIntArray(this.tag.copy());
    }

    @Override
    public @NotNull String getAsString() {
        return this.tag.getAsString();
    }

    @Override
    public NbtInt get(int index) {
        IntTag tag = this.tag.get(index);
        return tag == null ? null : new BukkitNbtInt(tag);
    }

    @Override
    public boolean add(int index, @NotNull NbtInt element) {
        return this.tag.addTag(index, ((BukkitNbtBase) element).getNms());
    }

    @Override
    public boolean set(int index, @NotNull NbtInt element) {
        return this.tag.setTag(index, ((BukkitNbtBase) element).getNms());
    }

    @Override
    public boolean add(@NotNull NbtInt element) {
        return this.tag.add((IntTag) ((BukkitNbtBase) element).getNms());
    }

    @Override
    public NbtInt remove(int index) {
        IntTag tag = this.tag.remove(index);
        return tag == null ? null : new BukkitNbtInt(tag);
    }

    @Override
    public int size() {
        return this.tag.size();
    }

    @Override
    public int[] getAsIntArray() {
        return this.tag.getAsIntArray();
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
