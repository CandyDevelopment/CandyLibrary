package fit.d6.candy.api.protocol;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface ProtocolService extends Service {

    @NotNull
    ProtocolManager getProtocolManager();

    @NotNull
    PacketManager getPacketManager();

    @NotNull
    static ProtocolService getService() {
        return CandyLibrary.getLibrary().getService(ProtocolService.class);
    }

}
