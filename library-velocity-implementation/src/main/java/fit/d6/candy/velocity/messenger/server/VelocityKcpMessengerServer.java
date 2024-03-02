package fit.d6.candy.velocity.messenger.server;

import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.MessengerServerCloser;
import fit.d6.candy.api.messenger.server.MessengerServerConnector;
import fit.d6.candy.api.messenger.server.MessengerServerReceiver;
import fit.d6.candy.velocity.messenger.VelocityKcpConnection;
import fit.d6.candy.velocity.messenger.VelocityPacketManager;
import fit.d6.candy.velocity.messenger.packet.*;
import io.netty.buffer.ByteBuf;
import kcp.ChannelConfig;
import kcp.KcpListener;
import kcp.KcpServer;
import kcp.Ukcp;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class VelocityKcpMessengerServer implements KcpListener, MessengerServer {

    private final KcpServer kcpServer = new KcpServer();

    private final MessengerServerConnector connector;
    private final MessengerServerReceiver receiver;
    private final MessengerServerCloser closer;

    public VelocityKcpMessengerServer(int port, VelocityServerOptions options) {
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
        this.connector.connect(this, new VelocityKcpConnection(ukcp));
    }

    @Override
    public void handleReceive(ByteBuf byteBuf, Ukcp ukcp) {
        int packetIdLength = byteBuf.readInt();
        String packetId = byteBuf.readCharSequence(packetIdLength, StandardCharsets.UTF_8).toString();
        Packet packet = VelocityPacketManager.tryToParse(packetId, new VelocityReadablePacketContent(byteBuf));
        if (packet instanceof PingPacket ping) {
            new VelocityKcpConnection(ukcp).send(new PongPacket(ping.getTimestamp()));
        }
        this.receiver.receive(this, new VelocityKcpConnection(ukcp), packet);
    }

    @Override
    public void handleException(Throwable throwable, Ukcp ukcp) {
        throwable.printStackTrace();
    }

    @Override
    public void handleClose(Ukcp ukcp) {
        this.closer.close(this, new VelocityKcpConnection(ukcp));
    }

    @Override
    public @NotNull MessengerProtocol getProtocol() {
        return MessengerProtocol.KCP;
    }

    @Override
    public void broadcast(@NotNull Packet packet) {
        VelocityWritablePacketContent writablePacketContent = new VelocityWritablePacketContent();
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
