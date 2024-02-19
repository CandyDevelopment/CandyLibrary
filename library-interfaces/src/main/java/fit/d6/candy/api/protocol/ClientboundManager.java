package fit.d6.candy.api.protocol;

import fit.d6.candy.api.protocol.packet.ClientboundDisconnectPacket;
import net.kyori.adventure.text.Component;

public interface ClientboundManager {

    ClientboundDisconnectPacket createDisconnectPacket(Component reason);

}
