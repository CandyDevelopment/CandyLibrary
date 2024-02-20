package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtShort extends NbtNumber {

    @Override
    @NotNull
    NbtShort copy();

    @NotNull
    static NbtShort of(short value) {
        return NbtService.getService().shortNbt(value);
    }

}
