package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtLong extends NbtNumber {

    @Override
    @NotNull
    NbtLong copy();

}
