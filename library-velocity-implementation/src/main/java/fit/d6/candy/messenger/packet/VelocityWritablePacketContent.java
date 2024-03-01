package fit.d6.candy.messenger.packet;

import fit.d6.candy.api.messenger.packet.WritablePacketContent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public class VelocityWritablePacketContent implements WritablePacketContent {

    private final ByteBuf byteBuf = Unpooled.buffer();

    @Override
    public void writeByte(byte value) {
        this.byteBuf.writeByte(value);
    }

    @Override
    public void writeShort(short value) {
        this.byteBuf.writeShort(value);
    }

    @Override
    public void writeInt(int value) {
        this.byteBuf.writeInt(value);
    }

    @Override
    public void writeLong(long value) {
        this.byteBuf.writeLong(value);
    }

    @Override
    public void writeFloat(float value) {
        this.byteBuf.writeFloat(value);
    }

    @Override
    public void writeDouble(double value) {
        this.byteBuf.writeDouble(value);
    }

    @Override
    public void writeString(@NotNull String value, @NotNull Charset charset) {
        this.byteBuf.writeCharSequence(value, charset);
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

}
