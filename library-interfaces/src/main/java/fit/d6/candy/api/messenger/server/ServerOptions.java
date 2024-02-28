package fit.d6.candy.api.messenger.server;

import fit.d6.candy.api.messenger.MessengerManager;
import org.jetbrains.annotations.NotNull;

public interface ServerOptions {

    @NotNull
    ServerOptions connector(@NotNull MessengerServerConnector connector);

    @NotNull
    ServerOptions receiver(@NotNull MessengerServerReceiver receiver);

    @NotNull
    ServerOptions closer(@NotNull MessengerServerCloser closer);

    @NotNull
    static ServerOptions of() {
        return MessengerManager.getManager().serverOptions();
    }

}
