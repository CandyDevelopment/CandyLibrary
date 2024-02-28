package fit.d6.candy.messenger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class WrappedTcpHandler extends ChannelInboundHandlerAdapter {

    private final TcpHandler tcpHandler;

    public WrappedTcpHandler(TcpHandler tcpHandler) {
        this.tcpHandler = tcpHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.tcpHandler.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.tcpHandler.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.tcpHandler.channelInactive(ctx);
    }

}
