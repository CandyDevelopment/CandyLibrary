package fit.d6.candy.bukkit.messenger;

import fit.d6.candy.api.messenger.Connection;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.bukkit.messenger.packet.BukkitWritablePacketContent;
import io.netty.buffer.ByteBuf;
import kcp.Ukcp;
import org.jetbrains.annotations.NotNull;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class BukkitKcpConnection implements Connection {

    private final Ukcp ukcp;

    public BukkitKcpConnection(Ukcp ukcp) {
        this.ukcp = ukcp;
    }

    @Override
    public int getConv() {
        return this.ukcp.getConv();
    }

    @Override
    public @NotNull SocketAddress getRemoteAddress() {
        return this.ukcp.user().getRemoteAddress();
    }

    @Override
    public void send(@NotNull Packet packet) {
        BukkitWritablePacketContent writablePacketContent = new BukkitWritablePacketContent();
        writablePacketContent.writeInt(packet.getType().getId().length());
        writablePacketContent.writeString(packet.getType().getId(), StandardCharsets.UTF_8);
        packet.serialize(writablePacketContent);
        ByteBuf byteBuf = writablePacketContent.getByteBuf();
        this.ukcp.write(byteBuf);
        byteBuf.release();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BukkitKcpConnection that = (BukkitKcpConnection) object;
        return Objects.equals(ukcp, that.ukcp) || Objects.equals(ukcp.user(), that.ukcp.user()) || Objects.equals(ukcp.getConv(), that.ukcp.getConv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ukcp.user());
    }

}
