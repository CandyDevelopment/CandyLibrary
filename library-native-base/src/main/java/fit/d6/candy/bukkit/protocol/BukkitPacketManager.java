package fit.d6.candy.bukkit.protocol;

import fit.d6.candy.api.protocol.ClientboundManager;
import fit.d6.candy.api.protocol.PacketManager;
import fit.d6.candy.api.protocol.ServerboundManager;
import org.jetbrains.annotations.NotNull;

public class BukkitPacketManager implements PacketManager {

    private final ClientboundManager clientboundManager = new BukkitClientboundManager();
    private final ServerboundManager serverboundManager = new BukkitServerboundManager();

    @Override
    public @NotNull ClientboundManager clientbound() {
        return this.clientboundManager;
    }

    @Override
    public @NotNull ServerboundManager serverbound() {
        return this.serverboundManager;
    }

}
