package fit.d6.candy.messenger.client;

import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.client.ClientOptions;
import fit.d6.candy.api.messenger.client.MessengerClientCloser;
import fit.d6.candy.api.messenger.client.MessengerClientConnector;
import fit.d6.candy.api.messenger.client.MessengerClientReceiver;
import org.jetbrains.annotations.NotNull;

public class BukkitClientOptions implements ClientOptions {

    private MessengerClientConnector connector = (self, connection) -> {
    };
    private MessengerClientReceiver receiver = (self, connection, packet) -> {
    };
    private MessengerClientCloser closer = self -> {
    };

    private boolean keepalive = false;
    private boolean conv = false;
    private MessengerProtocol protocol = MessengerProtocol.KCP;

    @Override
    public @NotNull ClientOptions connector(@NotNull MessengerClientConnector connector) {
        this.connector = connector;
        return this;
    }

    @Override
    public @NotNull ClientOptions receiver(@NotNull MessengerClientReceiver receiver) {
        this.receiver = receiver;
        return this;
    }

    @Override
    public @NotNull ClientOptions closer(@NotNull MessengerClientCloser closer) {
        this.closer = closer;
        return this;
    }

    @Override
    public @NotNull ClientOptions keepalive(boolean keepalive) {
        this.keepalive = keepalive;
        return this;
    }

    @Override
    public @NotNull ClientOptions conv(boolean conv) {
        this.conv = conv;
        return this;
    }

    @Override
    public @NotNull ClientOptions protocol(@NotNull MessengerProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public MessengerClientConnector getConnector() {
        return connector;
    }

    public MessengerClientReceiver getReceiver() {
        return receiver;
    }

    public MessengerClientCloser getCloser() {
        return closer;
    }

    public boolean isKeepalive() {
        return keepalive;
    }

    public boolean isConv() {
        return conv;
    }

    public MessengerProtocol getProtocol() {
        return protocol;
    }

}
