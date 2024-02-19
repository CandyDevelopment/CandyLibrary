package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NbtIntArray extends NbtBase {

    @Nullable
    NbtInt get(int index);

    boolean add(int index, @NotNull NbtInt element);

    boolean set(int index, @NotNull NbtInt element);

    boolean add(@NotNull NbtInt element);

    @Nullable
    NbtInt remove(int index);

    int size();

    int[] getAsIntArray();

    void clear();

}
