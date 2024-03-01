package fit.d6.candy.messenger.server;

import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.MessengerServerCloser;
import fit.d6.candy.api.messenger.server.MessengerServerConnector;
import fit.d6.candy.api.messenger.server.MessengerServerReceiver;
import fit.d6.candy.exception.MessengerException;
import fit.d6.candy.messenger.BukkitPacketManager;
import fit.d6.candy.messenger.BukkitTcpConnection;
import fit.d6.candy.messenger.packet.BukkitReadablePacketContent;
import fit.d6.candy.messenger.packet.BukkitWritablePacketContent;
import fit.d6.candy.messenger.packet.PingPacket;
import fit.d6.candy.messenger.packet.PongPacket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BukkitTcpMessengerServer extends ChannelInboundHandlerAdapter implements MessengerServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private final ChannelFuture channelFuture;

    private final Map<Channel, BukkitTcpConnection> channels = new HashMap<>();

    private final MessengerServerConnector connector;
    private final MessengerServerReceiver receiver;
    private final MessengerServerCloser closer;

    public BukkitTcpMessengerServer(int port, BukkitServerOptions options) {
        this.connector = options.getConnector();
        this.receiver = options.getReceiver();
        this.closer = options.getCloser();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_SNDBUF, 1024)
                .childOption(ChannelOption.SO_RCVBUF, 1024)
                .childOption(ChannelOption.SO_TIMEOUT, 10000)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channels.put(channel, new BukkitTcpConnection(channel));
                        channel.pipeline().addLast(BukkitTcpMessengerServer.this);
                    }
                });

        try {
            this.channelFuture = bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            throw new MessengerException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.connector.connect(this, this.channels.get(ctx.channel()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf byteBuf) {
            int packetIdLength = byteBuf.readInt();
            String packetId = byteBuf.readCharSequence(packetIdLength, StandardCharsets.UTF_8).toString();
            Packet packet = BukkitPacketManager.tryToParse(packetId, new BukkitReadablePacketContent(byteBuf));
            BukkitTcpConnection connection = this.channels.get(ctx.channel());
            if (packet instanceof PingPacket ping) {
                connection.send(new PongPacket(ping.getTimestamp()));
            }
            this.receiver.receive(this, connection, packet);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.closer.close(this, channels.remove(ctx.channel()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }

    @Override
    public @NotNull MessengerProtocol getProtocol() {
        return MessengerProtocol.TCP;
    }

    @Override
    public void broadcast(@NotNull Packet packet) {
        BukkitWritablePacketContent writablePacketContent = new BukkitWritablePacketContent();
        writablePacketContent.writeInt(packet.getType().getId().length());
        writablePacketContent.writeString(packet.getType().getId(), StandardCharsets.UTF_8);
        packet.serialize(writablePacketContent);
        ByteBuf byteBuf = writablePacketContent.getByteBuf();
        this.channels.keySet().forEach(channel -> channel.writeAndFlush(byteBuf));
    }

    @Override
    public void close() {
        this.channelFuture.channel().close();
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }

}
