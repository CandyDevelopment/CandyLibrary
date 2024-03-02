package fit.d6.candy.bukkit.protocol;

import fit.d6.candy.bukkit.nms.NmsAccessor;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

public class PacketHandler extends ChannelDuplexHandler {

    private final BukkitProtocolManager protocolManager;
    private final Logger logger;
    private volatile Player player;

    public PacketHandler(BukkitProtocolManager protocolManager, Logger logger, Player player) {
        this.protocolManager = protocolManager;
        this.logger = logger;
        this.player = player;
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // Called during player disconnection

        // Clean data structures
        this.protocolManager.injectedChannels.remove(ctx.channel());

        super.channelUnregistered(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (player == null && NmsAccessor.getAccessor().isPacketLoginOutSuccess(packet)) {
            // Player object should be in cache. If it's not, then it'll be PlayerJoinEvent to set the player
            Player player = this.protocolManager.playerCache.remove(NmsAccessor.getAccessor().getUUIDFromPacketLoginOutSuccess(packet));

            // Set the player only if it was contained into the cache
            if (player != null) {
                this.player = player;
            }
        }

        Object newPacket;
        try {
            newPacket = this.protocolManager.resolveSend(player, ctx.channel(), packet);
        } catch (OutOfMemoryError error) {
            // Out of memory, re-throw and return immediately
            throw error;
        } catch (Throwable throwable) {
            this.logger.error("[ProtocolService] An error occurred while calling onPacketSendAsync:", throwable);
            super.write(ctx, packet, promise);
            return;
        }
        if (newPacket != null)
            super.write(ctx, newPacket, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        Object newPacket;
        try {
            newPacket = this.protocolManager.resolveReceive(player, ctx.channel(), packet);
        } catch (OutOfMemoryError error) {
            // Out of memory, re-throw and return immediately
            throw error;
        } catch (Throwable throwable) {
            this.logger.error("[ProtocolService] An error occurred while calling onPacketReceiveAsync:", throwable);
            super.channelRead(ctx, packet);
            return;
        }
        if (newPacket != null)
            super.channelRead(ctx, newPacket);
    }

}
