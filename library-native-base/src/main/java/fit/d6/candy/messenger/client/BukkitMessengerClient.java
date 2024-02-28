package fit.d6.candy.messenger.client;

import fit.d6.candy.api.messenger.Connection;
import fit.d6.candy.api.messenger.client.MessengerClient;
import fit.d6.candy.api.messenger.client.MessengerClientCloser;
import fit.d6.candy.api.messenger.client.MessengerClientConnector;
import fit.d6.candy.api.messenger.client.MessengerClientReceiver;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.messenger.BukkitConnection;
import fit.d6.candy.messenger.BukkitPacketManager;
import fit.d6.candy.messenger.packet.BukkitReadablePacketContent;
import fit.d6.candy.messenger.packet.ClosePacket;
import fit.d6.candy.messenger.packet.PingPacket;
import fit.d6.candy.messenger.packet.PongPacket;
import io.netty.buffer.ByteBuf;
import kcp.ChannelConfig;
import kcp.KcpClient;
import kcp.KcpListener;
import kcp.Ukcp;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class BukkitMessengerClient implements KcpListener, MessengerClient {

    private final KcpClient kcpClient = new KcpClient();
    private final Timer timer = new Timer();
    private final BukkitConnection connection;

    private final MessengerClientConnector connector;
    private final MessengerClientReceiver receiver;
    private final MessengerClientCloser closer;

    public BukkitMessengerClient(int conv, boolean keepalive, InetAddress address, int port, MessengerClientConnector connector, MessengerClientReceiver receiver, MessengerClientCloser closer) {
        this.connector = connector;
        this.receiver = receiver;
        this.closer = closer;

        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.nodelay(true, 40, 2, true);
        channelConfig.setSndwnd(1024);
        channelConfig.setRcvwnd(1024);
        channelConfig.setMtu(1400);
        channelConfig.setConv(conv);
        channelConfig.setUseConvChannel(true);
        channelConfig.setAckNoDelay(false);
        channelConfig.setCrc32Check(true);
        channelConfig.setTimeoutMillis(10000);

        kcpClient.init(channelConfig);
        this.connection = new BukkitConnection(kcpClient.connect(new InetSocketAddress(address, port), channelConfig, this));
        if (keepalive) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        connection.send(new PingPacket());
                    } catch (Exception e) {
                        close();
                    }
                }
            }, 1000, 1000);
        }
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
        if (packet instanceof PongPacket pong) {
            System.out.println("Delay: " + (pong.getSend() - pong.getReceive()));
            return;
        } else if (packet instanceof ClosePacket) {
            this.close();
            return;
        }
        this.receiver.receive(this, this.connection, packet);
    }

    @Override
    public void handleException(Throwable throwable, Ukcp ukcp) {
        throwable.printStackTrace();
    }

    @Override
    public void handleClose(Ukcp ukcp) {
        this.closer.close(this);
    }

    @Override
    public @NotNull Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() {
        this.timer.cancel();
        this.kcpClient.stop();
    }

}
