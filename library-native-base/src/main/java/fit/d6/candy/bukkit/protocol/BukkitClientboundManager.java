package fit.d6.candy.bukkit.protocol;

import fit.d6.candy.api.protocol.ClientboundManager;
import fit.d6.candy.api.protocol.packet.ClientboundDisconnectPacket;
import fit.d6.candy.bukkit.protocol.packet.BukkitClientboundDisconnectPacket;
import net.kyori.adventure.text.Component;

public class BukkitClientboundManager implements ClientboundManager {

    @Override
    public ClientboundDisconnectPacket createDisconnectPacket(Component reason) {
        return new BukkitClientboundDisconnectPacket(null, reason);
    }

}
