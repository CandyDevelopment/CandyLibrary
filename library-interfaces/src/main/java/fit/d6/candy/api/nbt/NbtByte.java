package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtByte extends NbtNumber {

    @Override
    @NotNull
    NbtByte copy();

    @NotNull
    static NbtByte of(byte value) {
        return NbtService.getService().byteNbt(value);
    }

}
