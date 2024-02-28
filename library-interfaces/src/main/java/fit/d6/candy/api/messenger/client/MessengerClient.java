package fit.d6.candy.api.messenger.client;

import fit.d6.candy.api.messenger.Connection;
import org.jetbrains.annotations.NotNull;

public interface MessengerClient {

    @NotNull
    Connection getConnection();

    void close();

}
