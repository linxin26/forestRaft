package co.solinx.forestRaft.netty;

import co.solinx.forestRaft.CallBack;
import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lin8x_000 on 2015-08-30.
 */
public class ServiceHandler extends ChannelInboundHandlerAdapter {
    Logger logger= LoggerFactory.getLogger(ServiceHandler.class);
    private static Map voteList=new HashMap<>();
    private CallBack callBack;

    public ServiceHandler(CallBack callBack) {
        this.callBack=callBack;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(" {} {}", ctx.channel().remoteAddress(), msg);
         if(!RaftContext.getStateType().equals(Raft.StateType.LEADER)&&msg.toString().indexOf("vote")!=-1){
             logger.info("j接收到请求投票");
             if(callBack!=null) {
                 callBack.run(msg);
             }
             ctx.writeAndFlush("ok");
         }else if(msg.toString().indexOf("heartBeat")!=-1){
             logger.info("leader heartBeat。。。");
             if(callBack!=null){
                 callBack.run(msg);
             }
         }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("channelActive");
    }
}
