package fit.d6.candy.api.messenger.packet;

import org.jetbrains.annotations.NotNull;

public interface Packet {

    void serialize(@NotNull WritablePacketContent content);

    @NotNull
    PacketType<?> getType();

}
