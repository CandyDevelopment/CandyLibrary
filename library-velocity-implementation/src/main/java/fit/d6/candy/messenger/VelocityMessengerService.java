package fit.d6.candy.messenger;

import fit.d6.candy.VelocityService;
import fit.d6.candy.api.messenger.MessengerManager;
import fit.d6.candy.api.messenger.MessengerService;
import fit.d6.candy.api.messenger.PacketManager;
import org.jetbrains.annotations.NotNull;

public class VelocityMessengerService extends VelocityService implements MessengerService {

    private final VelocityMessengerManager messengerManager = new VelocityMessengerManager();
    private final VelocityPacketManager packetManager = new VelocityPacketManager();

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
