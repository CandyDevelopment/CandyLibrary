package fit.d6.candy.messenger;

import fit.d6.candy.api.messenger.Connection;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.messenger.packet.VelocityWritablePacketContent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.jetbrains.annotations.NotNull;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

public class VelocityWebSocketConnection implements Connection {

    private final Channel channel;

    public VelocityWebSocketConnection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public int getConv() {
        return 0;
    }

    @Override
    public @NotNull SocketAddress getRemoteAddress() {
        return this.channel.remoteAddress();
    }

    @Override
    public void send(@NotNull Packet packet) {
        VelocityWritablePacketContent writablePacketContent = new VelocityWritablePacketContent();
        writablePacketContent.writeInt(packet.getType().getId().length());
        writablePacketContent.writeString(packet.getType().getId(), StandardCharsets.UTF_8);
        packet.serialize(writablePacketContent);
        ByteBuf byteBuf = writablePacketContent.getByteBuf();
        this.channel.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
    }

    public Channel getChannel() {
        return channel;
    }

}
