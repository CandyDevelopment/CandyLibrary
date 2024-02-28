package fit.d6.candy.messenger.client;

import fit.d6.candy.api.messenger.Connection;
import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.client.MessengerClient;
import fit.d6.candy.api.messenger.client.MessengerClientCloser;
import fit.d6.candy.api.messenger.client.MessengerClientConnector;
import fit.d6.candy.api.messenger.client.MessengerClientReceiver;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.messenger.BukkitPacketManager;
import fit.d6.candy.messenger.BukkitTcpConnection;
import fit.d6.candy.messenger.packet.BukkitReadablePacketContent;
import fit.d6.candy.messenger.packet.ClosePacket;
import fit.d6.candy.messenger.packet.PingPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class BukkitTcpMessengerClient extends ChannelInboundHandlerAdapter implements MessengerClient {

    private final EventLoopGroup group = new NioEventLoopGroup();
    private final BukkitTcpConnection connection;

    private final Timer timer = new Timer();

    private final MessengerClientConnector connector;
    private final MessengerClientReceiver receiver;
    private final MessengerClientCloser closer;

    public BukkitTcpMessengerClient(InetAddress address, int port, BukkitClientOptions options) {
        this.connector = options.getConnector();
        this.receiver = options.getReceiver();
        this.closer = options.getCloser();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(this.group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(BukkitTcpMessengerClient.this);
                    }
                });

        try {
            this.connection = new BukkitTcpConnection(bootstrap.connect(new InetSocketAddress(address, port)).sync().channel());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (options.isKeepalive()) {
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        connector.connect(BukkitTcpMessengerClient.this, connection);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf byteBuf) {
            int packetIdLength = byteBuf.readInt();
            String packetId = byteBuf.readCharSequence(packetIdLength, StandardCharsets.UTF_8).toString();
            Packet packet = BukkitPacketManager.tryToParse(packetId, new BukkitReadablePacketContent(byteBuf));
            if (packet instanceof ClosePacket) {
                BukkitTcpMessengerClient.this.close();
                return;
            }
            BukkitTcpMessengerClient.this.receiver.receive(BukkitTcpMessengerClient.this, BukkitTcpMessengerClient.this.connection, packet);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        BukkitTcpMessengerClient.this.closer.close(BukkitTcpMessengerClient.this);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }

    @Override
    public @NotNull MessengerProtocol getProtocol() {
        return MessengerProtocol.TCP;
    }

    @Override
    public @NotNull Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() {
        this.timer.cancel();
        this.connection.getChannel().close();
        this.group.shutdownGracefully();
    }

}
