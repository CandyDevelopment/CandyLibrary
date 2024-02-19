package fit.d6.candy.nms.v1_20_R2.nbt;

import fit.d6.candy.api.nbt.NbtBase;
import fit.d6.candy.api.nbt.NbtCompound;
import fit.d6.candy.api.nbt.NbtList;
import fit.d6.candy.api.nbt.NbtType;
import fit.d6.candy.exception.NbtException;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BukkitNbtCompound extends BukkitNbtBase implements NbtCompound {

    private final CompoundTag tag;

    public BukkitNbtCompound(CompoundTag nms) {
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
        return NbtType.COMPOUND;
    }

    @Override
    public @NotNull String getAsString() {
        return this.tag.getAsString();
    }

    @Override
    public int size() {
        return this.tag.size();
    }

    @Override
    public @NotNull Set<String> getAllKeys() {
        return this.tag.getAllKeys();
    }

    @Override
    public NbtBase put(@NotNull String key, NbtBase element) {
        BukkitNbtBase bukkitNbtBase = (BukkitNbtBase) element;

        Tag newTag = this.tag.put(key, bukkitNbtBase.getNms());
        if (newTag == null)
            return null;

        return nmsToCandy(newTag);
    }

    @Override
    public void putByte(@NotNull String key, byte value) {
        this.tag.putByte(key, value);
    }

    @Override
    public void putShort(@NotNull String key, short value) {
        this.tag.putShort(key, value);
    }

    @Override
    public void putInt(@NotNull String key, int value) {
        this.tag.putInt(key, value);
    }

    @Override
    public void putLong(@NotNull String key, long value) {
        this.tag.putLong(key, value);
    }

    @Override
    public void putUUID(@NotNull String key, UUID value) {
        this.tag.putUUID(key, value);
    }

    @Override
    public UUID getUUID(@NotNull String key) {
        return this.tag.getUUID(key);
    }

    @Override
    public boolean hasUUID(@NotNull String key) {
        return this.tag.hasUUID(key);
    }

    @Override
    public void putFloat(@NotNull String key, float value) {
        this.tag.putFloat(key, value);
    }

    @Override
    public void putDouble(@NotNull String key, double value) {
        this.tag.putDouble(key, value);
    }

    @Override
    public void putString(@NotNull String key, String value) {
        this.tag.putString(key, value);
    }

    @Override
    public void putByteArray(@NotNull String key, byte[] value) {
        this.tag.putByteArray(key, value);
    }

    @Override
    public void putByteArray(@NotNull String key, List<Byte> value) {
        this.tag.putByteArray(key, value);
    }

    @Override
    public void putIntArray(@NotNull String key, int[] value) {
        this.tag.putIntArray(key, value);
    }

    @Override
    public void putIntArray(@NotNull String key, List<Integer> value) {
        this.tag.putIntArray(key, value);
    }

    @Override
    public void putLongArray(@NotNull String key, long[] value) {
        this.tag.putLongArray(key, value);
    }

    @Override
    public void putLongArray(@NotNull String key, List<Long> value) {
        this.tag.putLongArray(key, value);
    }

    @Override
    public void putBoolean(@NotNull String key, boolean value) {
        this.tag.putBoolean(key, value);
    }

    @Nullable
    @Override
    public NbtBase get(String key) {
        Tag tag = this.tag.get(key);
        return tag == null ? null : nmsToCandy(tag);
    }

    @Override
    @NotNull
    public NbtType getElementType(@NotNull String key) {
        return NbtType.getById(this.tag.getTagType(key));
    }

    @Override
    public boolean contains(@NotNull String key) {
        return this.tag.contains(key);
    }

    @Override
    public boolean contains(@NotNull String key, @NotNull NbtType type) {
        return this.tag.contains(key, type.getId());
    }

    @Override
    public byte getByte(@NotNull String key) {
        return this.tag.getByte(key);
    }

    @Override
    public short getShort(@NotNull String key) {
        return this.tag.getShort(key);
    }

    @Override
    public int getInt(@NotNull String key) {
        return this.tag.getInt(key);
    }

    @Override
    public long getLong(@NotNull String key) {
        return this.tag.getLong(key);
    }

    @Override
    public float getFloat(@NotNull String key) {
        return this.tag.getFloat(key);
    }

    @Override
    public double getDouble(@NotNull String key) {
        return this.tag.getDouble(key);
    }

    @Override
    public String getString(@NotNull String key) {
        return this.tag.getString(key);
    }

    @Override
    public byte[] getByteArray(@NotNull String key) {
        return this.tag.getByteArray(key);
    }

    @Override
    public int[] getIntArray(@NotNull String key) {
        return this.tag.getIntArray(key);
    }

    @Override
    public long[] getLongArray(@NotNull String key) {
        return this.tag.getLongArray(key);
    }

    @Override
    public @NotNull NbtCompound getCompound(@NotNull String key) {
        return new BukkitNbtCompound(this.tag.getCompound(key));
    }

    @Override
    public @NotNull NbtList getList(@NotNull String key, @NotNull NbtType type) {
        return new BukkitNbtList(this.tag.getList(key, type.getId()));
    }

    @Override
    public boolean getBoolean(@NotNull String key) {
        return this.tag.getBoolean(key);
    }

    @Override
    public void remove(@NotNull String key) {
        this.tag.remove(key);
    }

    @Override
    public boolean isEmpty() {
        return this.tag.isEmpty();
    }

    @Override
    public @NotNull NbtCompound copy() {
        return new BukkitNbtCompound(this.tag.copy());
    }

    @Override
    public @NotNull NbtCompound merge(@NotNull NbtCompound source) {
        this.tag.merge((CompoundTag) ((BukkitNbtBase) source).getNms());
        return this;
    }

    @Override
    public Tag getNms() {
        return this.tag;
    }

    public static NbtBase nmsToCandy(Tag tag) {
        if (tag.getId() == 12) {
            return new BukkitNbtLongArray((LongArrayTag) tag);
        } else if (tag.getId() == 11) {
            return new BukkitNbtIntArray((IntArrayTag) tag);
        } else if (tag.getId() == 10) {
            return new BukkitNbtCompound((CompoundTag) tag);
        } else if (tag.getId() == 9) {
            return new BukkitNbtList((ListTag) tag);
        } else if (tag.getId() == 8) {
            return new BukkitNbtString((StringTag) tag);
        } else if (tag.getId() == 7) {
            return new BukkitNbtByteArray((ByteArrayTag) tag);
        } else if (tag.getId() == 6) {
            return new BukkitNbtDouble((DoubleTag) tag);
        } else if (tag.getId() == 5) {
            return new BukkitNbtFloat((FloatTag) tag);
        } else if (tag.getId() == 4) {
            return new BukkitNbtLong((LongTag) tag);
        } else if (tag.getId() == 3) {
            return new BukkitNbtInt((IntTag) tag);
        } else if (tag.getId() == 2) {
            return new BukkitNbtShort((ShortTag) tag);
        } else if (tag.getId() == 1) {
            return new BukkitNbtByte((ByteTag) tag);
        } else if (tag.getId() == 0) {
            return BukkitNbtEnd.INSTANCE;
        }
        throw new NbtException("Unknown nbt type");
    }

}
