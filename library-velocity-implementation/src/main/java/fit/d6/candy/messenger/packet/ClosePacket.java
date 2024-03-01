package fit.d6.candy.messenger.packet;

import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.packet.PacketType;
import fit.d6.candy.api.messenger.packet.ReadablePacketContent;
import fit.d6.candy.api.messenger.packet.WritablePacketContent;
import org.jetbrains.annotations.NotNull;

public class ClosePacket implements Packet {

    public ClosePacket(ReadablePacketContent readablePacketContent) {
    }

    public ClosePacket() {
    }

    @Override
    public void serialize(@NotNull WritablePacketContent content) {
    }

    @Override
    public @NotNull PacketType<?> getType() {
        return ClosePacketType.INSTANCE;
    }

}
