package fit.d6.candy.bukkit.protocol;

import fit.d6.candy.api.protocol.PacketListener;
import fit.d6.candy.api.protocol.ProtocolListener;
import fit.d6.candy.api.protocol.RawPacketListener;
import fit.d6.candy.api.protocol.packet.PacketType;
import fit.d6.candy.bukkit.exception.ProtocolException;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class BukkitProtocolListener implements ProtocolListener {

    private final BukkitProtocolManager protocolManager;
    private final Plugin plugin;

    public BukkitProtocolListener(BukkitProtocolManager protocolManager, Plugin plugin) {
        this.protocolManager = protocolManager;
        this.plugin = plugin;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void register(@NotNull PacketType type, @NotNull PacketListener listener) {
        if (!type.isListenable())
            throw new ProtocolException("This packet type is not listenable");
        if (!this.protocolManager.packedListeners.containsKey(type))
            this.protocolManager.packedListeners.put(type, new HashMap<>());
        if (!this.protocolManager.packedListeners.get(type).containsKey(this.plugin))
            this.protocolManager.packedListeners.get(type).put(this.plugin, new ArrayList<>());

        this.protocolManager.packedListeners.get(type).get(this.plugin).add(listener);
    }

    @Override
    public void register(@NotNull RawPacketListener listener) {
        this.protocolManager.rawPacketListeners.add(listener);
    }

}
