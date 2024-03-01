package fit.d6.candy.api.messenger.client;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessengerClientCloser {

    void close(@NotNull MessengerClient self);

}
