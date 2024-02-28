package fit.d6.candy.messenger;

import fit.d6.candy.api.messenger.MessengerManager;
import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.client.ClientOptions;
import fit.d6.candy.api.messenger.client.MessengerClient;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.ServerOptions;
import fit.d6.candy.messenger.client.BukkitClientOptions;
import fit.d6.candy.messenger.client.BukkitKcpMessengerClient;
import fit.d6.candy.messenger.client.BukkitTcpMessengerClient;
import fit.d6.candy.messenger.server.BukkitKcpMessengerServer;
import fit.d6.candy.messenger.server.BukkitServerOptions;
import fit.d6.candy.messenger.server.BukkitTcpMessengerServer;
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
        if (clientOptions.getProtocol() == MessengerProtocol.TCP)
            return new BukkitTcpMessengerClient(address, port, clientOptions);
        return new BukkitKcpMessengerClient(conv, address, port, clientOptions);
    }

    @Override
    public @NotNull MessengerServer server(int port, @NotNull ServerOptions options) {
        BukkitServerOptions serverOptions = (BukkitServerOptions) options;
        if (serverOptions.getProtocol() == MessengerProtocol.TCP)
            return new BukkitTcpMessengerServer(port, serverOptions);
        return new BukkitKcpMessengerServer(port, (BukkitServerOptions) options);
    }

}
