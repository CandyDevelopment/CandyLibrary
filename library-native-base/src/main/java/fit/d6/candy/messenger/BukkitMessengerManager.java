package fit.d6.candy.messenger;

import fit.d6.candy.api.messenger.MessengerManager;
import fit.d6.candy.api.messenger.client.ClientOptions;
import fit.d6.candy.api.messenger.client.MessengerClient;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.ServerOptions;
import fit.d6.candy.messenger.client.BukkitClientOptions;
import fit.d6.candy.messenger.client.BukkitMessengerClient;
import fit.d6.candy.messenger.server.BukkitMessengerServer;
import fit.d6.candy.messenger.server.BukkitServerOptions;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

public class BukkitMessengerManager implements MessengerManager {

    @Override
    public @NotNull ClientOptions clientOptions() {
        return new BukkitClientOptions();
    }

    @Override
    public @NotNull ServerOptions serverOptions() {
        return new BukkitServerOptions();
    }

    @Override
    public @NotNull MessengerClient client(int conv, @NotNull InetAddress address, int port, @NotNull ClientOptions options) {
        BukkitClientOptions clientOptions = (BukkitClientOptions) options;
        return new BukkitMessengerClient(conv, clientOptions.isKeepalive(), address, port, clientOptions.getConnector(), clientOptions.getReceiver(), clientOptions.getCloser());
    }

    @Override
    public @NotNull MessengerServer server(int port, @NotNull ServerOptions options) {
        BukkitServerOptions serverOptions = (BukkitServerOptions) options;
        return new BukkitMessengerServer(port, serverOptions.getConnector(), serverOptions.getReceiver(), serverOptions.getCloser());
    }

}
