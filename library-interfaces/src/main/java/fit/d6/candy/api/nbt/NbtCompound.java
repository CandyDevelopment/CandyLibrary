package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NbtCompound extends NbtBase {

    int size();

    @NotNull
    Set<String> getAllKeys();

    @Nullable
    NbtBase put(@NotNull String key, NbtBase element);

    void putByte(@NotNull String key, byte value);

    void putShort(@NotNull String key, short value);

    void putInt(@NotNull String key, int value);

    void putLong(@NotNull String key, long value);

    void putUUID(@NotNull String key, UUID value);

    @Nullable
    UUID getUUID(@NotNull String key);

    boolean hasUUID(@NotNull String key);

    void putFloat(@NotNull String key, float value);

    void putDouble(@NotNull String key, double value);

    void putString(@NotNull String key, String value);

    void putByteArray(@NotNull String key, byte[] value);

    void putByteArray(@NotNull String key, List<Byte> value);

    void putIntArray(@NotNull String key, int[] value);

    void putIntArray(@NotNull String key, List<Integer> value);

    void putLongArray(@NotNull String key, long[] value);

    void putLongArray(@NotNull String key, List<Long> value);

    void putBoolean(@NotNull String key, boolean value);

    @Nullable
    NbtBase get(String key);

    @NotNull
    NbtType getElementType(@NotNull String key);

    boolean contains(@NotNull String key);

    boolean contains(@NotNull String key, NbtType type);

    byte getByte(@NotNull String key);

    short getShort(@NotNull String key);

    int getInt(@NotNull String key);

    long getLong(@NotNull String key);

    float getFloat(@NotNull String key);

    double getDouble(@NotNull String key);

    @NotNull
    String getString(@NotNull String key);

    byte[] getByteArray(@NotNull String key);

    int[] getIntArray(@NotNull String key);

    long[] getLongArray(@NotNull String key);

    @NotNull
    NbtCompound getCompound(@NotNull String key);

    @NotNull
    NbtList getList(@NotNull String key, @NotNull NbtType type);

    boolean getBoolean(@NotNull String key);

    void remove(@NotNull String key);

    boolean isEmpty();

    @Override
    @NotNull
    NbtCompound copy();

    @NotNull
    NbtCompound merge(@NotNull NbtCompound source);

    @NotNull
    static NbtCompound of() {
        return NbtService.getService().compoundNbt();
    }

}
