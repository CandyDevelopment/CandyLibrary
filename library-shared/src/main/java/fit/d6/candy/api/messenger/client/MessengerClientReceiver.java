package fit.d6.candy.api.messenger.client;

import fit.d6.candy.api.messenger.Connection;
import fit.d6.candy.api.messenger.packet.Packet;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessengerClientReceiver {

    void receive(@NotNull MessengerClient self, @NotNull Connection connection, @NotNull Packet packet);

}
