package fit.d6.candy.api.messenger;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

public interface SimpleAddress extends Address {

    @NotNull
    InetAddress getHost();

    int getPort();

}
