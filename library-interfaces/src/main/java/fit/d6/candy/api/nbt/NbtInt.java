package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtInt extends NbtNumber {

    @Override
    @NotNull
    NbtInt copy();

}
