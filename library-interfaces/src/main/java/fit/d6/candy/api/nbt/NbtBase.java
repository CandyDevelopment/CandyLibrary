package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;

public interface NbtBase {

    void write(@NotNull DataOutput output);

    int getSizeInBytes();

    byte getId();

    @NotNull
    NbtType getType();

    @NotNull
    NbtBase copy();

    @NotNull
    String getAsString();

}
