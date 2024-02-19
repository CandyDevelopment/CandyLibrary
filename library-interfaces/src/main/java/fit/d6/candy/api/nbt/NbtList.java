package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NbtList extends NbtBase {

    @Nullable
    NbtBase remove(int index);

    boolean isEmpty();

    @NotNull
    NbtCompound getCompound(int index);

    @NotNull
    NbtList getList(int index);

    short getShort(int index);

    int getInt(int index);

    int[] getIntArray(int index);

    long[] getLongArray(int index);

    double getDouble(int index);

    float getFloat(int index);

    @NotNull
    String getString(int index);

    int size();

    @Nullable
    NbtBase get(int index);

    boolean set(int index, @NotNull NbtBase element);

    boolean add(int index, @NotNull NbtBase element);

    boolean add(@NotNull NbtBase element);

    void clear();

    @NotNull
    NbtType getElementType();

    @Override
    @NotNull
    NbtList copy();

}
