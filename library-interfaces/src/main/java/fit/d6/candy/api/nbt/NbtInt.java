package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtInt extends NbtNumber {

    @Override
    @NotNull
    NbtInt copy();

    @NotNull
    static NbtInt of(int value) {
        return NbtService.getService().intNbt(value);
    }

}
