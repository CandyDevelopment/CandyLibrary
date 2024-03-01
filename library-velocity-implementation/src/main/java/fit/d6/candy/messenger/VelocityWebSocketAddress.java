package fit.d6.candy.messenger;

import fit.d6.candy.api.messenger.WebSocketAddress;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class VelocityWebSocketAddress implements WebSocketAddress {

    private final URI uri;

    public VelocityWebSocketAddress(URI uri) {
        this.uri = uri;
    }

    @Override
    public @NotNull URI getUri() {
        return uri;
    }

}
