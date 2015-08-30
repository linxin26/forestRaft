package co.solinx.forestRaft.netty;

import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lin8x_000 on 2015-08-30.
 */
public class ServiceHandler extends ChannelInboundHandlerAdapter {

    Logger logger= LoggerFactory.getLogger(ServiceHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(" {} {}", ctx.channel().remoteAddress(),msg);
        if(msg.toString().split(",")[0].equals("0")){
            ctx.writeAndFlush("follower");

        }else if(msg.toString().split(",")[0].equals("1")){
            ctx.writeAndFlush("Ok"+msg.toString().split(",")[1]);
        }else if(msg.toString().split(",")[0].equals("2")){
            if(RaftContext.getStateType()== Raft.StateType.LEADER) {
                ctx.writeAndFlush("leader,1");
            }else{
                ctx.writeAndFlush("leader,0");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive");
    }
}
