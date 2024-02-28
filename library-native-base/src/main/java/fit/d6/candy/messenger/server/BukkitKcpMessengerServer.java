package fit.d6.candy.messenger.server;

import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.MessengerServerCloser;
import fit.d6.candy.api.messenger.server.MessengerServerConnector;
import fit.d6.candy.api.messenger.server.MessengerServerReceiver;
import fit.d6.candy.messenger.BukkitConnection;
import fit.d6.candy.messenger.BukkitPacketManager;
import fit.d6.candy.messenger.packet.*;
import io.netty.buffer.ByteBuf;
import kcp.ChannelConfig;
import kcp.KcpListener;
import kcp.KcpServer;
import kcp.Ukcp;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class BukkitKcpMessengerServer implements KcpListener, MessengerServer {

    private final KcpServer kcpServer = new KcpServer();

    private final MessengerServerConnector connector;
    private final MessengerServerReceiver receiver;
    private final MessengerServerCloser closer;

    public BukkitKcpMessengerServer(int port, BukkitServerOptions options) {
        this.connector = options.getConnector();
        this.receiver = options.getReceiver();
        this.closer = options.getCloser();

        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.nodelay(true, 40, 2, true);
        channelConfig.setSndwnd(1024);
        channelConfig.setRcvwnd(1024);
        channelConfig.setMtu(1400);
        if (options.isConv())
            channelConfig.setUseConvChannel(true);
        channelConfig.setTimeoutMillis(10000);
        channelConfig.setAckNoDelay(false);
        channelConfig.setCrc32Check(true);

        kcpServer.init(this, channelConfig, port);

        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void onConnected(Ukcp ukcp) {
        this.connector.connect(this, new BukkitConnection(ukcp));
    }

    @Override
    public void handleReceive(ByteBuf byteBuf, Ukcp ukcp) {
        int packetIdLength = byteBuf.readInt();
        String packetId = byteBuf.readCharSequence(packetIdLength, StandardCharsets.UTF_8).toString();
        Packet packet = BukkitPacketManager.tryToParse(packetId, new BukkitReadablePacketContent(byteBuf));
        if (packet instanceof PingPacket ping) {
            new BukkitConnection(ukcp).send(new PongPacket(ping.getTimestamp()));
        }
        this.receiver.receive(this, new BukkitConnection(ukcp), packet);
    }

    @Override
    public void handleException(Throwable throwable, Ukcp ukcp) {
        throwable.printStackTrace();
    }

    @Override
    public void handleClose(Ukcp ukcp) {
        this.closer.close(this, new BukkitConnection(ukcp));
    }

    @Override
    public @NotNull MessengerProtocol getProtocol() {
        return MessengerProtocol.KCP;
    }

    @Override
    public void broadcast(@NotNull Packet packet) {
        BukkitWritablePacketContent writablePacketContent = new BukkitWritablePacketContent();
        writablePacketContent.writeInt(packet.getType().getId().length());
        writablePacketContent.writeString(packet.getType().getId(), StandardCharsets.UTF_8);
        packet.serialize(writablePacketContent);
        ByteBuf byteBuf = writablePacketContent.getByteBuf();

        this.kcpServer.getChannelManager().getAll().forEach(ukcp -> ukcp.write(byteBuf));

        byteBuf.release();
    }

    @Override
    public void close() {
        broadcast(new ClosePacket());
        this.kcpServer.stop();
    }

}
