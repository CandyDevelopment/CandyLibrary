package fit.d6.candy.messenger.server;

import fit.d6.candy.api.messenger.server.MessengerServerCloser;
import fit.d6.candy.api.messenger.server.MessengerServerConnector;
import fit.d6.candy.api.messenger.server.MessengerServerReceiver;
import fit.d6.candy.api.messenger.server.ServerOptions;
import org.jetbrains.annotations.NotNull;

public class BukkitServerOptions implements ServerOptions {

    private MessengerServerConnector connector = (self, connection) -> {
    };
    private MessengerServerReceiver receiver = (self, connection, packet) -> {
    };
    private MessengerServerCloser closer = (self, connection) -> {
    };

    @Override
    public @NotNull ServerOptions connector(@NotNull MessengerServerConnector connector) {
        this.connector = connector;
        return this;
    }

    @Override
    public @NotNull ServerOptions receiver(@NotNull MessengerServerReceiver receiver) {
        this.receiver = receiver;
        return this;
    }

    @Override
    public @NotNull ServerOptions closer(@NotNull MessengerServerCloser closer) {
        this.closer = closer;
        return this;
    }

    public MessengerServerConnector getConnector() {
        return connector;
    }

    public MessengerServerReceiver getReceiver() {
        return receiver;
    }

    public MessengerServerCloser getCloser() {
        return closer;
    }

}
