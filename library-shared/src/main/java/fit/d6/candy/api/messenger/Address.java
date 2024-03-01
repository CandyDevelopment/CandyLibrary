package fit.d6.candy.api.messenger;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.URI;

public interface Address {

    @NotNull
    static SimpleAddress simple(@NotNull InetAddress host, int port) {
        return MessengerManager.getManager().address(host, port);
    }

    @NotNull
    static WebSocketAddress websocket(@NotNull URI uri) {
        return MessengerManager.getManager().address(uri);
    }

}
