package fit.d6.candy.api.messenger.client;

import fit.d6.candy.api.messenger.Connection;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessengerClientConnector {

    void connect(@NotNull MessengerClient self, @NotNull Connection connection);

}
