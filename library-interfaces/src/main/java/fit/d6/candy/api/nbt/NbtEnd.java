package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtEnd extends NbtBase {

    @NotNull
    static NbtEnd of() {
        return NbtService.getService().endNbt();
    }

}
