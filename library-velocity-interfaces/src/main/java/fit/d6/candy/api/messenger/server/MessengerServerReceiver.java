package fit.d6.candy.api.messenger.server;

import fit.d6.candy.api.messenger.Connection;
import fit.d6.candy.api.messenger.packet.Packet;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessengerServerReceiver {

    void receive(@NotNull MessengerServer self, @NotNull Connection connection, @NotNull Packet packet);

}
