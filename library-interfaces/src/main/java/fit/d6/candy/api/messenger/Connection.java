package fit.d6.candy.api.messenger;

import fit.d6.candy.api.messenger.packet.Packet;
import org.jetbrains.annotations.NotNull;

import java.net.SocketAddress;

public interface Connection {

    int getConv();

    @NotNull
    SocketAddress getRemoteAddress();

    void send(@NotNull Packet packet);

}
