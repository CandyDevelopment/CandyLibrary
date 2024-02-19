package fit.d6.candy.api.protocol;

import fit.d6.candy.api.protocol.packet.Packet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PacketListener {

    void resolve(@NotNull Player player, @NotNull Packet packet);

}
