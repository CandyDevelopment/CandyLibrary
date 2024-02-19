package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NbtByteArray extends NbtBase {

    @Nullable
    NbtByte get(int index);

    boolean add(int index, @NotNull NbtByte element);

    boolean set(int index, @NotNull NbtByte element);

    boolean add(NbtByte element);

    @Nullable
    NbtByte remove(int index);

    int size();

    byte @NotNull [] getAsByteArray();

    void clear();

}
