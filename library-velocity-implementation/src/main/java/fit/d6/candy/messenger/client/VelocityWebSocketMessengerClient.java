package fit.d6.candy.messenger.client;

import fit.d6.candy.api.messenger.Connection;
import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.WebSocketAddress;
import fit.d6.candy.api.messenger.client.MessengerClient;
import fit.d6.candy.api.messenger.client.MessengerClientCloser;
import fit.d6.candy.api.messenger.client.MessengerClientConnector;
import fit.d6.candy.api.messenger.client.MessengerClientReceiver;
import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.messenger.VelocityPacketManager;
import fit.d6.candy.messenger.VelocityWebSocketConnection;
import fit.d6.candy.messenger.packet.ClosePacket;
import fit.d6.candy.messenger.packet.PingPacket;
import fit.d6.candy.messenger.packet.VelocityReadablePacketContent;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class VelocityWebSocketMessengerClient extends ChannelInboundHandlerAdapter implements MessengerClient {

    private final EventLoopGroup group = new NioEventLoopGroup();
    private final VelocityWebSocketConnection connection;

    private final Timer timer = new Timer();

    private final MessengerClientConnector connector;
    private final MessengerClientReceiver receiver;
    private final MessengerClientCloser closer;

    public VelocityWebSocketMessengerClient(VelocityClientOptions options) {
        this.connector = options.getConnector();
        this.receiver = options.getReceiver();
        this.closer = options.getCloser();

        URI uri = ((WebSocketAddress) options.getAddress()).getUri();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(this.group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_SNDBUF, 1024)
                .option(ChannelOption.SO_RCVBUF, 1024)
                .option(ChannelOption.SO_TIMEOUT, 10000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new HttpClientCodec())
                                .addLast(new ChunkedWriteHandler())
                                .addLast(new HttpObjectAggregator(65536))
                                .addLast(new WebSocketClientProtocolHandler(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders())))
                                .addLast(VelocityWebSocketMessengerClient.this);
                    }
                });

        try {
            this.connection = new VelocityWebSocketConnection(bootstrap.connect(new InetSocketAddress(uri.getHost(), uri.getPort())).sync().channel());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (options.isKeepalive()) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        connection.send(new PingPacket());
                        connection.getChannel().writeAndFlush(new PingWebSocketFrame());
                    } catch (Exception e) {
                        close();
                    }
                }
            }, 1000, 1000);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (Objects.equals(evt, WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE)) {
            this.connector.connect(this, this.connection);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof BinaryWebSocketFrame webSocketFrame) {
            ByteBuf byteBuf = webSocketFrame.content();
            int packetIdLength = byteBuf.readInt();
            String packetId = byteBuf.readCharSequence(packetIdLength, StandardCharsets.UTF_8).toString();
            Packet packet = VelocityPacketManager.tryToParse(packetId, new VelocityReadablePacketContent(byteBuf));
            if (packet instanceof ClosePacket) {
                this.close();
                return;
            }
            this.receiver.receive(this, this.connection, packet);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.closer.close(this);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }

    @Override
    public @NotNull MessengerProtocol getProtocol() {
        return MessengerProtocol.WEBSOCKET;
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
