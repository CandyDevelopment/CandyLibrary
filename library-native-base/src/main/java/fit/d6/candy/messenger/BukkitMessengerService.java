package fit.d6.candy.messenger;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.messenger.MessengerManager;
import fit.d6.candy.api.messenger.MessengerService;
import fit.d6.candy.api.messenger.PacketManager;
import org.jetbrains.annotations.NotNull;

public class BukkitMessengerService extends BukkitService implements MessengerService {

    private final BukkitMessengerManager messengerManager = new BukkitMessengerManager();
    private final BukkitPacketManager packetManager = new BukkitPacketManager();

    @Override
    public @NotNull String getId() {
        return "messenger";
    }

    @Override
    public @NotNull MessengerManager getMessengerManager() {
        return this.messengerManager;
    }

    @Override
    public @NotNull PacketManager getPacketManager() {
        return this.packetManager;
    }

}
