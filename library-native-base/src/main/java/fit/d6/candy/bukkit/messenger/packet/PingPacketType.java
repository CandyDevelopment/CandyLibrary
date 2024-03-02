package fit.d6.candy.bukkit.messenger.packet;

import fit.d6.candy.api.messenger.packet.PacketType;
import org.jetbrains.annotations.NotNull;

public class PingPacketType implements PacketType<PingPacket> {

    public final static PingPacketType INSTANCE = new PingPacketType();

    private PingPacketType() {
    }

    @Override
    public @NotNull String getId() {
        return "ping";
    }

}
