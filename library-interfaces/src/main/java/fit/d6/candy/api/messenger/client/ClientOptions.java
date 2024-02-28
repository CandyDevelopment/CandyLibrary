package fit.d6.candy.api.messenger.client;

import fit.d6.candy.api.messenger.MessengerManager;
import org.jetbrains.annotations.NotNull;

public interface ClientOptions {

    @NotNull
    ClientOptions connector(@NotNull MessengerClientConnector connector);

    @NotNull
    ClientOptions receiver(@NotNull MessengerClientReceiver receiver);

    @NotNull
    ClientOptions closer(@NotNull MessengerClientCloser closer);

    @NotNull
    ClientOptions keepalive(boolean keepalive);

    @NotNull
    static ClientOptions of() {
        return MessengerManager.getManager().clientOptions();
    }

}
