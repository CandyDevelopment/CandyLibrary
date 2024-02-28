package fit.d6.candy.api.messenger;

import fit.d6.candy.api.messenger.client.ClientOptions;
import fit.d6.candy.api.messenger.client.MessengerClient;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.ServerOptions;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

public interface MessengerManager {

    @NotNull
    ClientOptions clientOptions();

    @NotNull
    ServerOptions serverOptions();

    @NotNull
    MessengerClient client(int conv, @NotNull InetAddress address, int port, @NotNull ClientOptions options);

    @NotNull
    MessengerServer server(int port, @NotNull ServerOptions options);

    @NotNull
    static MessengerManager getManager() {
        return MessengerService.getService().getMessengerManager();
    }

}
