package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtFloat extends NbtNumber {

    @Override
    @NotNull
    NbtFloat copy();

    @NotNull
    static NbtFloat of(float value) {
        return NbtService.getService().floatNbt(value);
    }

}
