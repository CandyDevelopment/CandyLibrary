package fit.d6.candy.api.messenger;

import fit.d6.candy.api.messenger.packet.Packet;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

public interface Connection {

    int getConv();

    @NotNull
    InetSocketAddress getRemoteAddress();

    void send(@NotNull Packet packet);

}
