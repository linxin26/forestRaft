package co.solinx.forestRaft.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lin8x_000 on 2015-08-30.
 */
public class ClientHandler  extends ChannelInboundHandlerAdapter {

    Logger logger= LoggerFactory.getLogger(ClientHandler.class);
    private static String result;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("{} {} ",ctx.channel().remoteAddress(),msg);
        this.result= (String) msg;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    public static String getResult() {
        return result;
    }
}
