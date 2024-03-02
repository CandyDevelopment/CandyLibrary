package fit.d6.candy.velocity.messenger;

import fit.d6.candy.api.messenger.MessengerManager;
import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.SimpleAddress;
import fit.d6.candy.api.messenger.WebSocketAddress;
import fit.d6.candy.api.messenger.client.ClientOptions;
import fit.d6.candy.api.messenger.client.MessengerClient;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.ServerOptions;
import fit.d6.candy.velocity.messenger.client.VelocityClientOptions;
import fit.d6.candy.velocity.messenger.client.VelocityKcpMessengerClient;
import fit.d6.candy.velocity.messenger.client.VelocityTcpMessengerClient;
import fit.d6.candy.velocity.messenger.client.VelocityWebSocketMessengerClient;
import fit.d6.candy.velocity.messenger.server.VelocityKcpMessengerServer;
import fit.d6.candy.velocity.messenger.server.VelocityServerOptions;
import fit.d6.candy.velocity.messenger.server.VelocityTcpMessengerServer;
import fit.d6.candy.velocity.messenger.server.VelocityWebSocketMessengerServer;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.URI;

public class VelocityMessengerManager implements MessengerManager {

    @Override
    public @NotNull SimpleAddress address(@NotNull InetAddress address, int port) {
        return new VelocitySimpleAddress(address, port);
    }

    @Override
    public @NotNull WebSocketAddress address(@NotNull URI uri) {
        return new VelocityWebSocketAddress(uri);
    }

    @Override
    public @NotNull ClientOptions clientOptions() {
        return new VelocityClientOptions();
    }

    @Override
    public @NotNull ServerOptions serverOptions() {
        return new VelocityServerOptions();
    }

    @Override
    public @NotNull MessengerClient client(int conv, @NotNull ClientOptions options) {
        VelocityClientOptions clientOptions = (VelocityClientOptions) options;
        if (clientOptions.getProtocol() == MessengerProtocol.TCP)
            return new VelocityTcpMessengerClient(clientOptions);
        else if (clientOptions.getProtocol() == MessengerProtocol.WEBSOCKET)
            return new VelocityWebSocketMessengerClient(clientOptions);
        return new VelocityKcpMessengerClient(conv, clientOptions);
    }

    @Override
    public @NotNull MessengerServer server(int port, @NotNull ServerOptions options) {
        VelocityServerOptions serverOptions = (VelocityServerOptions) options;
        if (serverOptions.getProtocol() == MessengerProtocol.TCP)
            return new VelocityTcpMessengerServer(port, serverOptions);
        else if (serverOptions.getProtocol() == MessengerProtocol.WEBSOCKET)
            return new VelocityWebSocketMessengerServer(port, serverOptions);
        return new VelocityKcpMessengerServer(port, serverOptions);
    }

}
