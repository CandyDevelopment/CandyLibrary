package fit.d6.candy.api.messenger.server;

import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.packet.Packet;
import org.jetbrains.annotations.NotNull;

public interface MessengerServer {

    @NotNull
    MessengerProtocol getProtocol();

    void broadcast(@NotNull Packet packet);

    void close();

}
