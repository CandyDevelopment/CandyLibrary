package fit.d6.candy.api.messenger.packet;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public interface WritablePacketContent {

    void writeByte(byte value);

    void writeShort(short value);

    void writeInt(int value);

    void writeLong(long value);

    void writeFloat(float value);

    void writeDouble(double value);

    void writeString(@NotNull String value, @NotNull Charset charset);

}
