package fit.d6.candy.messenger.server;

import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.server.MessengerServerCloser;
import fit.d6.candy.api.messenger.server.MessengerServerConnector;
import fit.d6.candy.api.messenger.server.MessengerServerReceiver;
import fit.d6.candy.api.messenger.server.ServerOptions;
import org.jetbrains.annotations.NotNull;

public class VelocityServerOptions implements ServerOptions {

    private MessengerServerConnector connector = (self, connection) -> {
    };
    private MessengerServerReceiver receiver = (self, connection, packet) -> {
    };
    private MessengerServerCloser closer = (self, connection) -> {
    };
    private boolean conv = false;
    private MessengerProtocol protocol = MessengerProtocol.KCP;
    private String websocketPath = "/";

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

    @Override
    public @NotNull ServerOptions conv(boolean conv) {
        this.conv = conv;
        return this;
    }

    @Override
    public @NotNull ServerOptions protocol(@NotNull MessengerProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    @Override
    public @NotNull ServerOptions websocketPath(@NotNull String path) {
        this.websocketPath = path;
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

    public boolean isConv() {
        return conv;
    }

    public MessengerProtocol getProtocol() {
        return protocol;
    }

    public String getWebsocketPath() {
        return websocketPath;
    }

}
