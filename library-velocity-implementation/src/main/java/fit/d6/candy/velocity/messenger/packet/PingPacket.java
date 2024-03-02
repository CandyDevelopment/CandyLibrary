package fit.d6.candy.velocity.messenger.packet;

import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.packet.PacketType;
import fit.d6.candy.api.messenger.packet.ReadablePacketContent;
import fit.d6.candy.api.messenger.packet.WritablePacketContent;
import org.jetbrains.annotations.NotNull;

public class PingPacket implements Packet {

    private final long timestamp;

    public PingPacket(ReadablePacketContent readablePacketContent) {
        this.timestamp = readablePacketContent.readLong();
    }

    public PingPacket() {
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public void serialize(@NotNull WritablePacketContent content) {
        content.writeLong(this.timestamp);
    }

    @Override
    public @NotNull PacketType<?> getType() {
        return PingPacketType.INSTANCE;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
