package fit.d6.candy.api.messenger;

import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.packet.PacketType;
import fit.d6.candy.api.messenger.packet.ReadablePacketContent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface PacketManager {

    <T extends Packet> void register(@NotNull PacketType<T> packetType, @NotNull Function<ReadablePacketContent, T> supplier);

}
