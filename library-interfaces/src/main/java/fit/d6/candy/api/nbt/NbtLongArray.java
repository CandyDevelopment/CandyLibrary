package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface NbtLongArray extends NbtBase {

    @Nullable
    NbtLong get(int index);

    boolean add(int index, @NotNull NbtLong element);

    boolean set(int index, @NotNull NbtLong element);

    boolean add(@NotNull NbtLong element);

    @Nullable
    NbtLong remove(int index);

    int size();

    long[] getAsLongArray();

    void clear();

    @NotNull
    static NbtLongArray of(long @NotNull [] longs) {
        return NbtService.getService().longArrayNbt(longs);
    }

    @NotNull
    static NbtLongArray of(@NotNull List<@NotNull Long> longs) {
        return NbtService.getService().longArrayNbt(longs);
    }

}
