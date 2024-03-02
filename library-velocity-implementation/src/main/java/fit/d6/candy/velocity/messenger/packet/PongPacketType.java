package fit.d6.candy.velocity.messenger.packet;

import fit.d6.candy.api.messenger.packet.PacketType;
import org.jetbrains.annotations.NotNull;

public class PongPacketType implements PacketType<PongPacket> {

    public final static PongPacketType INSTANCE = new PongPacketType();

    private PongPacketType() {
    }

    @Override
    public @NotNull String getId() {
        return "pong";
    }

}
