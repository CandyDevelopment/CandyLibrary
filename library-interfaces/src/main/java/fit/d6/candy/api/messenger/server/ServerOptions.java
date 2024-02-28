package fit.d6.candy.api.messenger.server;

import fit.d6.candy.api.messenger.MessengerManager;
import fit.d6.candy.api.messenger.MessengerProtocol;
import org.jetbrains.annotations.NotNull;

public interface ServerOptions {

    @NotNull
    ServerOptions connector(@NotNull MessengerServerConnector connector);

    @NotNull
    ServerOptions receiver(@NotNull MessengerServerReceiver receiver);

    @NotNull
    ServerOptions closer(@NotNull MessengerServerCloser closer);

    @NotNull
    ServerOptions conv(boolean conv);

    @NotNull
    ServerOptions protocol(@NotNull MessengerProtocol protocol);

    @NotNull
    static ServerOptions of() {
        return MessengerManager.getManager().serverOptions();
    }

}
