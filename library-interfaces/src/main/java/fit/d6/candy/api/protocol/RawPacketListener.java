package fit.d6.candy.api.protocol;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RawPacketListener {

    void resolve(@NotNull Player player, @NotNull PacketBound bound, @NotNull Object packet);

}
