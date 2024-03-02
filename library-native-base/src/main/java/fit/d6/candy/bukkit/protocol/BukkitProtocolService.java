package fit.d6.candy.bukkit.protocol;

import fit.d6.candy.api.protocol.ProtocolManager;
import fit.d6.candy.api.protocol.ProtocolService;
import fit.d6.candy.bukkit.BukkitService;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BukkitProtocolService extends BukkitService implements ProtocolService {

    private final BukkitProtocolManager protocolManager = new BukkitProtocolManager();
    private final BukkitPacketManager packetManager = new BukkitPacketManager();

    @Override
    public @NotNull String getId() {
        return "protocol";
    }

    @Override
    public @NotNull ProtocolManager getProtocolManager() {
        return this.protocolManager;
    }

    @NotNull
    @Override
    public BukkitPacketManager getPacketManager() {
        return packetManager;
    }

    @Override
    public void enable(Plugin plugin) {
        this.protocolManager.init(plugin);
    }

    @Override
    public void disable(Plugin plugin) {
        try {
            this.protocolManager.close();
        } catch (Exception ignored) {
        }
    }

}
