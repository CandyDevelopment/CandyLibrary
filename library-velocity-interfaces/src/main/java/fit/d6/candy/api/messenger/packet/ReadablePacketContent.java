package fit.d6.candy.api.messenger.packet;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public interface ReadablePacketContent {

    byte readByte();

    short readShort();

    int readInt();

    long readLong();

    float readFloat();

    double readDouble();

    @NotNull
    String readString(int length, @NotNull Charset charset);

    int readableBytes();

}
