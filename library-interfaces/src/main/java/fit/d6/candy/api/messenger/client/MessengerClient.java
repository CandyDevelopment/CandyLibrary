package fit.d6.candy.api.messenger.client;

import fit.d6.candy.api.messenger.Connection;
import fit.d6.candy.api.messenger.MessengerProtocol;
import org.jetbrains.annotations.NotNull;

public interface MessengerClient {

    @NotNull
    MessengerProtocol getProtocol();

    @NotNull
    Connection getConnection();

    void close();

}
