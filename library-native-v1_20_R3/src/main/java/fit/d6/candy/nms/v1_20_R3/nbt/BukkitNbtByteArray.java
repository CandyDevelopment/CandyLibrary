package fit.d6.candy.nms.v1_20_R3.nbt;

import fit.d6.candy.api.nbt.NbtBase;
import fit.d6.candy.api.nbt.NbtByte;
import fit.d6.candy.api.nbt.NbtByteArray;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class BukkitNbtByteArray extends BukkitNbtBase implements NbtByteArray {

    private final ByteArrayTag tag;

    public BukkitNbtByteArray(ByteArrayTag nms) {
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
        return NbtType.BYTE_ARRAY;
    }

    @Override
    public @NotNull NbtBase copy() {
        return new BukkitNbtByteArray((ByteArrayTag) this.tag.copy());
    }

    @Override
    public @NotNull String getAsString() {
        return this.tag.getAsString();
    }

    @Override
    public NbtByte get(int index) {
        ByteTag tag = this.tag.get(index);
        return tag == null ? null : new BukkitNbtByte(tag);
    }

    @Override
    public boolean add(int index, @NotNull NbtByte element) {
        return this.tag.addTag(index, ((BukkitNbtBase) element).getNms());
    }

    @Override
    public boolean set(int index, @NotNull NbtByte element) {
        return this.tag.setTag(index, ((BukkitNbtBase) element).getNms());
    }

    @Override
    public boolean add(NbtByte element) {
        return this.tag.add((ByteTag) ((BukkitNbtBase) element).getNms());
    }

    @Override
    public NbtByte remove(int index) {
        ByteTag tag = this.tag.remove(index);
        return tag == null ? null : new BukkitNbtByte(tag);
    }

    @Override
    public int size() {
        return this.tag.size();
    }

    @Override
    public byte @NotNull [] getAsByteArray() {
        return this.tag.getAsByteArray();
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
