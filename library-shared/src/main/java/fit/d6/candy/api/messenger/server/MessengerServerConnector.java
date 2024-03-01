package fit.d6.candy.api.messenger.server;

import fit.d6.candy.api.messenger.Connection;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessengerServerConnector {

    void connect(@NotNull MessengerServer self, @NotNull Connection connection);

}
