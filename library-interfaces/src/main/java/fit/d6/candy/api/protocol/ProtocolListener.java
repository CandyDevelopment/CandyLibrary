package fit.d6.candy.api.protocol;

import fit.d6.candy.api.protocol.packet.PacketType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface ProtocolListener {

    @NotNull
    Plugin getPlugin();

    void register(@NotNull PacketType type, @NotNull PacketListener listener);

    void register(@NotNull RawPacketListener listener);

}
