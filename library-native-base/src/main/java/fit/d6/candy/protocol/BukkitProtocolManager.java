package fit.d6.candy.protocol;

import fit.d6.candy.api.protocol.*;
import fit.d6.candy.api.protocol.packet.PacketType;
import fit.d6.candy.exception.ProtocolException;
import fit.d6.candy.nms.NmsAccessor;
import fit.d6.candy.protocol.packet.BukkitPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.*;

public class BukkitProtocolManager implements ProtocolManager {

    final Map<UUID, Player> playerCache = Collections.synchronizedMap(new HashMap<>());
    final Set<Channel> injectedChannels = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));
    private final List<Object> networkManagers = new ArrayList<>();
    private final EventListener listener = new EventListener(this);
    private Logger logger;
    private NmsAccessor accessor;

    private final Map<Plugin, ProtocolListener> listeners = new HashMap<>();

    final Map<PacketType, Map<Plugin, List<PacketListener>>> packedListeners = new HashMap<>();

    final List<RawPacketListener> rawPacketListeners = new ArrayList<>();

    public BukkitProtocolManager() {
    }

    void init(Plugin plugin) {
        this.accessor = NmsAccessor.getAccessor();
        this.logger = plugin.getSLF4JLogger();
        Bukkit.getPluginManager().registerEvents(this.listener, plugin);
        this.networkManagers.addAll(NmsAccessor.getAccessor().getNetworkManagers());

        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                PacketHandler handler = new PacketHandler(this, this.logger, player);
                NmsAccessor.getAccessor().injectPlayer(handler, player, this.injectedChannels);
            } catch (Exception exception) {
                this.logger.error("[ProtocolService] An error occurred while injecting a player:", exception);
            }
        }
    }

    Object resolveReceive(Player player, Channel channel, Object packet) {
        for (RawPacketListener packetListener : this.rawPacketListeners)
            try {
                packetListener.resolve(player, PacketBound.SERVER, packet);
            } catch (Exception ignored) {
            }
        return packet;
    }

    Object resolveSend(Player player, Channel channel, Object packet) {
        BukkitPacket pkt = null;
        if (this.accessor.packetIsClientboundPlayerChat(packet)) {
            pkt = (BukkitPacket) this.accessor.packetClientboundPlayerChatPacketAsCandy(packet);
        } else if (this.accessor.packetIsClientboundDisconnect(packet)) {
            pkt = (BukkitPacket) this.accessor.packetClientboundDisconnectPacketAsCandy(packet);
        }

        if (pkt != null) {
            for (List<PacketListener> listeners : this.packedListeners.get(PacketType.CLIENTBOUND_PLAYER_CHAT).values()) {
                for (PacketListener listener : listeners) {
                    try {
                        listener.resolve(player, pkt);
                    } catch (Exception e) {
                        this.logger.error("[ProtocolService] error occurs", e);
                    }
                }
            }
            if (pkt.isCancelled())
                return null;
            if (pkt.isModified())
                return this.accessor.packetAsVanilla(pkt);
        }
        for (RawPacketListener packetListener : this.rawPacketListeners)
            try {
                packetListener.resolve(player, PacketBound.CLIENT, packet);
            } catch (Exception ignored) {
            }
        return packet;
    }

    public final void close() {
        HandlerList.unregisterAll(listener);

        synchronized (networkManagers) {
            for (Object manager : networkManagers) {
                try {
                    Channel channel = NmsAccessor.getAccessor().getChannelWithNetworkManager(manager);

                    channel.eventLoop().submit(() -> channel.pipeline().remove("CandyLibrary-ProtocolService"));
                } catch (Exception exception) {
                    this.logger.error("[ProtocolService] An error occurred while uninjecting a player:", exception);
                }
            }
        }

        playerCache.clear();
        injectedChannels.clear();
    }

    @Override
    public @NotNull ProtocolListener createListener(@NotNull Plugin plugin) {
        if (this.listeners.containsKey(plugin))
            throw new ProtocolException("This plugin has already register a listener");
        BukkitProtocolListener protocolListener = new BukkitProtocolListener(this, plugin);
        this.listeners.put(plugin, protocolListener);
        return protocolListener;
    }

    private final static class EventListener implements Listener {

        private final BukkitProtocolManager protocolManager;

        public EventListener(BukkitProtocolManager protocolManager) {
            this.protocolManager = protocolManager;
        }

        @EventHandler(priority = EventPriority.LOWEST)
        private void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
            synchronized (this.protocolManager.networkManagers) { // Lock out main thread
                if (this.protocolManager.networkManagers instanceof RandomAccess) {
                    for (int i = this.protocolManager.networkManagers.size() - 1; i >= 0; i--) {
                        Object networkManager = this.protocolManager.networkManagers.get(i);
                        NmsAccessor.getAccessor().injectNetworkManager(protocolManager, protocolManager.logger, networkManager, protocolManager.injectedChannels);
                    }
                } else {
                    for (Object networkManager : this.protocolManager.networkManagers) {
                        NmsAccessor.getAccessor().injectNetworkManager(protocolManager, protocolManager.logger, networkManager, protocolManager.injectedChannels);
                    }
                }
            }
        }

        @EventHandler(priority = EventPriority.LOWEST)
        private void onPlayerLoginEvent(PlayerLoginEvent event) {
            this.protocolManager.playerCache.put(event.getPlayer().getUniqueId(), event.getPlayer());
        }

        @EventHandler(priority = EventPriority.LOWEST)
        private void onPlayerJoinEvent(PlayerJoinEvent event) {
            Player player = event.getPlayer();

            Channel channel = NmsAccessor.getAccessor().getPlayerChannel(player);
            ChannelHandler channelHandler = channel.pipeline().get("CandyLibrary-ProtocolService");
            if (channelHandler != null) {
                if (channelHandler instanceof PacketHandler) {
                    this.protocolManager.playerCache.remove(player.getUniqueId());
                }
                return;
            }

            this.protocolManager.logger.info("[ProtocolService] Late injection for player " + player.getName());

            PacketHandler handler = new PacketHandler(this.protocolManager, this.protocolManager.logger, player);
            NmsAccessor.getAccessor().injectPlayer(handler, player, this.protocolManager.injectedChannels);
        }

        @EventHandler(priority = EventPriority.MONITOR)
        private void onPluginDisableEvent(PluginDisableEvent event) {
            if (event.getPlugin().getName().equals("CandyLibrary")) {
                this.protocolManager.close();
            }
            this.protocolManager.listeners.remove(event.getPlugin());
            this.protocolManager.packedListeners.values().forEach(map -> map.remove(event.getPlugin()));
        }

    }


}
