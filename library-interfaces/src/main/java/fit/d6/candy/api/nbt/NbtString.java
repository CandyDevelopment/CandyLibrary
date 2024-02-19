package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtString extends NbtBase {

    int length();

    @Override
    @NotNull
    NbtString copy();

}
