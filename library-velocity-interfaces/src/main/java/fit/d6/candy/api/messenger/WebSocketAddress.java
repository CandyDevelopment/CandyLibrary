package fit.d6.candy.api.messenger;

import org.jetbrains.annotations.NotNull;

import java.net.URI;

public interface WebSocketAddress extends Address {

    @NotNull
    URI getUri();

}
