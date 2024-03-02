package fit.d6.candy.bukkit.messenger.packet;

import fit.d6.candy.api.messenger.packet.ReadablePacketContent;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public class BukkitReadablePacketContent implements ReadablePacketContent {

    private final ByteBuf byteBuf;

    public BukkitReadablePacketContent(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public byte readByte() {
        return this.byteBuf.readByte();
    }

    @Override
    public short readShort() {
        return this.byteBuf.readShort();
    }

    @Override
    public int readInt() {
        return this.byteBuf.readInt();
    }

    @Override
    public long readLong() {
        return this.byteBuf.readLong();
    }

    @Override
    public float readFloat() {
        return this.byteBuf.readFloat();
    }

    @Override
    public double readDouble() {
        return this.byteBuf.readDouble();
    }

    @Override
    public @NotNull String readString(int length, @NotNull Charset charset) {
        return this.byteBuf.readCharSequence(length, charset).toString();
    }

    @Override
    public int readableBytes() {
        return this.byteBuf.readableBytes();
    }

}
