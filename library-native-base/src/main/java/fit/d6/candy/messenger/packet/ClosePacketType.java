package fit.d6.candy.messenger.packet;

import fit.d6.candy.api.messenger.packet.PacketType;
import org.jetbrains.annotations.NotNull;

public class ClosePacketType implements PacketType<ClosePacket> {

    public final static ClosePacketType INSTANCE = new ClosePacketType();

    private ClosePacketType() {
    }

    @Override
    public @NotNull String getId() {
        return "close";
    }

}
