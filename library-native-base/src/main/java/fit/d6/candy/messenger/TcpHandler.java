package fit.d6.candy.messenger;

import io.netty.channel.ChannelHandlerContext;

public interface TcpHandler {

    void channelActive(ChannelHandlerContext ctx) throws Exception;

    void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception;

    void channelInactive(ChannelHandlerContext ctx) throws Exception;

}
