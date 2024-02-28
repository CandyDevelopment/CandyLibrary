package fit.d6.candy.messenger.server;

import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.server.MessengerServer;
import fit.d6.candy.api.messenger.server.MessengerServerCloser;
import fit.d6.candy.api.messenger.server.MessengerServerConnector;
import fit.d6.candy.api.messenger.server.MessengerServerReceiver;
import fit.d6.candy.exception.MessengerException;
import fit.d6.candy.messenger.BukkitPacketManager;
import fit.d6.candy.messenger.BukkitWebSocketConnection;
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
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BukkitWebSocketMessengerServer extends ChannelInboundHandlerAdapter implements MessengerServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private final ChannelFuture channelFuture;

    private final Map<Channel, BukkitWebSocketConnection> channels = new HashMap<>();

    private final MessengerServerConnector connector;
    private final MessengerServerReceiver receiver;
    private final MessengerServerCloser closer;

    public BukkitWebSocketMessengerServer(int port, BukkitServerOptions options) {
        this.connector = options.getConnector();
        this.receiver = options.getReceiver();
        this.closer = options.getCloser();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channels.put(channel, new BukkitWebSocketConnection(channel));
                        channel.pipeline()
                                .addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(65536))
                                .addLast(new WebSocketServerProtocolHandler(options.getWebsocketPath(), null, false, 1024 * 1024 * 50, false, true, 10000L))
                                .addLast(BukkitWebSocketMessengerServer.this);
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
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            this.connector.connect(this, this.channels.get(ctx.channel()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof BinaryWebSocketFrame binaryWebSocketFrame) {
            ByteBuf byteBuf = binaryWebSocketFrame.content();
            int packetIdLength = byteBuf.readInt();
            String packetId = byteBuf.readCharSequence(packetIdLength, StandardCharsets.UTF_8).toString();
            Packet packet = BukkitPacketManager.tryToParse(packetId, new BukkitReadablePacketContent(byteBuf));
            BukkitWebSocketConnection connection = this.channels.get(ctx.channel());
            if (packet instanceof PingPacket ping) {
                connection.send(new PongPacket(ping.getTimestamp()));
            }
            this.receiver.receive(this, connection, packet);
        } else if (msg instanceof PingWebSocketFrame pingWebSocketFrame) {
            ctx.channel().writeAndFlush(new PongWebSocketFrame(pingWebSocketFrame.content().retain()));
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
        return MessengerProtocol.WEBSOCKET;
    }

    @Override
    public void broadcast(@NotNull Packet packet) {
        BukkitWritablePacketContent writablePacketContent = new BukkitWritablePacketContent();
        writablePacketContent.writeInt(packet.getType().getId().length());
        writablePacketContent.writeString(packet.getType().getId(), StandardCharsets.UTF_8);
        packet.serialize(writablePacketContent);
        ByteBuf byteBuf = writablePacketContent.getByteBuf();
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(byteBuf);
        this.channels.keySet().forEach(channel -> channel.writeAndFlush(frame));
    }

    @Override
    public void close() {
        this.channelFuture.channel().close();
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }

}
