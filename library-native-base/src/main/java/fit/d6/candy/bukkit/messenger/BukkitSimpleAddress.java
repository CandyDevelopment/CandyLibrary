package fit.d6.candy.bukkit.messenger;

import fit.d6.candy.api.messenger.SimpleAddress;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

public class BukkitSimpleAddress implements SimpleAddress {

    private final InetAddress address;
    private final int port;

    public BukkitSimpleAddress(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    @NotNull
    public InetAddress getHost() {
        return address;
    }

    @Override
    public int getPort() {
        return port;
    }

}
