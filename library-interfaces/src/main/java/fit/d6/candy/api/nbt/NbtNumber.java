package fit.d6.candy.api.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtNumber extends NbtBase {

    long getAsLong();

    int getAsInt();

    short getAsShort();

    byte getAsByte();

    double getAsDouble();

    float getAsFloat();

    @NotNull
    Number getAsNumber();

}
