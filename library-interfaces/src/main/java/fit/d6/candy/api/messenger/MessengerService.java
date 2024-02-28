package fit.d6.candy.api.messenger;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface MessengerService extends Service {

    @NotNull
    MessengerManager getMessengerManager();

    @NotNull
    PacketManager getPacketManager();

    @NotNull
    static MessengerService getService() {
        return CandyLibrary.getLibrary().getService(MessengerService.class);
    }

}
