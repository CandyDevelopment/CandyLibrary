package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtByte extends NbtNumber {

    @Override
    @NotNull
    NbtByte copy();

}
