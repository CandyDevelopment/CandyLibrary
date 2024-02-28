package fit.d6.candy.api.messenger.packet;

import org.jetbrains.annotations.NotNull;

public interface PacketType<T extends Packet> {

    @NotNull
    String getId();

}
