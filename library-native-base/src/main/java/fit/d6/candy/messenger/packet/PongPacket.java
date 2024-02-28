package fit.d6.candy.messenger.packet;

import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.packet.PacketType;
import fit.d6.candy.api.messenger.packet.ReadablePacketContent;
import fit.d6.candy.api.messenger.packet.WritablePacketContent;
import org.jetbrains.annotations.NotNull;

public class PongPacket implements Packet {

    private final long receive;
    private final long send;

    public PongPacket(ReadablePacketContent readablePacketContent) {
        this.receive = readablePacketContent.readLong();
        this.send = readablePacketContent.readLong();
    }

    public PongPacket(long receive) {
        this.receive = receive;
        this.send = System.currentTimeMillis();
    }

    @Override
    public void serialize(@NotNull WritablePacketContent content) {
        content.writeLong(this.receive);
        content.writeLong(this.send);
    }

    @Override
    public @NotNull PacketType<?> getType() {
        return PongPacketType.INSTANCE;
    }

    public long getReceive() {
        return receive;
    }

    public long getSend() {
        return send;
    }

}
