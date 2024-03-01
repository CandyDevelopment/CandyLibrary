package fit.d6.candy.messenger;

import fit.d6.candy.api.messenger.PacketManager;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.packet.PacketType;
import fit.d6.candy.api.messenger.packet.ReadablePacketContent;
import fit.d6.candy.exception.MessengerException;
import fit.d6.candy.messenger.packet.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class VelocityPacketManager implements PacketManager {

    private final static Map<String, PacketType<?>> packetTypeMap = new HashMap<>();
    private final static Map<PacketType<?>, Function<ReadablePacketContent, Packet>> functionMap = new HashMap<>();

    static {
        packetTypeMap.put("ping", PingPacketType.INSTANCE);
        packetTypeMap.put("pong", PongPacketType.INSTANCE);
        packetTypeMap.put("close", ClosePacketType.INSTANCE);
        functionMap.put(PingPacketType.INSTANCE, PingPacket::new);
        functionMap.put(PongPacketType.INSTANCE, PongPacket::new);
        functionMap.put(ClosePacketType.INSTANCE, ClosePacket::new);
    }

    @Override
    public <T extends Packet> void register(@NotNull PacketType<T> packetType, @NotNull Function<ReadablePacketContent, T> supplier) {
        if (packetTypeMap.containsKey(packetType.getId()))
            throw new MessengerException("PacketType with this id has been registered");
        packetTypeMap.put(packetType.getId(), packetType);
        functionMap.put(packetType, supplier::apply);
    }

    public static Packet tryToParse(String packetId, ReadablePacketContent readablePacketContent) {
        if (!packetTypeMap.containsKey(packetId))
            throw new MessengerException("PacketType with this id not exists");
        return functionMap.get(packetTypeMap.get(packetId)).apply(readablePacketContent);
    }

}
