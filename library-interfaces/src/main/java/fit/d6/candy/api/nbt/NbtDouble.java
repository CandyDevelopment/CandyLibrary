package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtDouble extends NbtNumber {

    @Override
    @NotNull
    NbtDouble copy();

}
