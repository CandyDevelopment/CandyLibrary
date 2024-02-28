package fit.d6.candy.messenger;

import fit.d6.candy.api.messenger.MessengerManager;
import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.SimpleAddress;
import fit.d6.candy.api.messenger.WebSocketAddress;
import fit.d6.candy.api.messenger.client.ClientOptions;
import fit.d6.candy.api.messenger.client.MessengerClient;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.ServerOptions;
import fit.d6.candy.messenger.client.BukkitClientOptions;
import fit.d6.candy.messenger.client.BukkitKcpMessengerClient;
import fit.d6.candy.messenger.client.BukkitTcpMessengerClient;
import fit.d6.candy.messenger.client.BukkitWebSocketMessengerClient;
import fit.d6.candy.messenger.server.BukkitKcpMessengerServer;
import fit.d6.candy.messenger.server.BukkitServerOptions;
import fit.d6.candy.messenger.server.BukkitTcpMessengerServer;
import fit.d6.candy.messenger.server.BukkitWebSocketMessengerServer;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.URI;

public class BukkitMessengerManager implements MessengerManager {

    @Override
    public @NotNull SimpleAddress address(@NotNull InetAddress address, int port) {
        return new BukkitSimpleAddress(address, port);
    }

    @Override
    public @NotNull WebSocketAddress address(@NotNull URI uri) {
        return new BukkitWebSocketAddress(uri);
    }

    @Override
    public @NotNull ClientOptions clientOptions() {
        return new BukkitClientOptions();
    }

    @Override
    public @NotNull ServerOptions serverOptions() {
        return new BukkitServerOptions();
    }

    @Override
    public @NotNull MessengerClient client(int conv, @NotNull ClientOptions options) {
        BukkitClientOptions clientOptions = (BukkitClientOptions) options;
        if (clientOptions.getProtocol() == MessengerProtocol.TCP)
            return new BukkitTcpMessengerClient(clientOptions);
        else if (clientOptions.getProtocol() == MessengerProtocol.WEBSOCKET)
            return new BukkitWebSocketMessengerClient(clientOptions);
        return new BukkitKcpMessengerClient(conv, clientOptions);
    }

    @Override
    public @NotNull MessengerServer server(int port, @NotNull ServerOptions options) {
        BukkitServerOptions serverOptions = (BukkitServerOptions) options;
        if (serverOptions.getProtocol() == MessengerProtocol.TCP)
            return new BukkitTcpMessengerServer(port, serverOptions);
        else if (serverOptions.getProtocol() == MessengerProtocol.WEBSOCKET)
            return new BukkitWebSocketMessengerServer(port, serverOptions);
        return new BukkitKcpMessengerServer(port, serverOptions);
    }

}
