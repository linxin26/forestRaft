package co.solinx.forestRaft.netty;

import co.solinx.forestRaft.CallBack;
import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftContext;
import co.solinx.forestRaft.log.RaftLog;
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
    Logger logger = LoggerFactory.getLogger(ServiceHandler.class);
    private static Map voteList = new HashMap<>();
    private CallBack callBack;
    private RaftLog log;

    public ServiceHandler(CallBack callBack, RaftLog log) {
        this.callBack = callBack;
        this.log = log;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("收到 {} {}", ctx.channel().remoteAddress(), msg);
        if (!RaftContext.getStateType().equals(Raft.StateType.LEADER) && msg.toString().indexOf("server") != -1) {
            String[] voteInfo=msg.toString().split(",");
            voteList.put(voteInfo[0],voteInfo[1]);
            if(Integer.valueOf(voteInfo[1])>log.curentTerm()){
                logger.info("接收到请求投票");
                if (callBack != null) {
                    callBack.run(msg);
                }
                log.setTerm(Long.parseLong(voteInfo[1]));
                ctx.writeAndFlush("ok");
            }else if(Integer.valueOf(voteInfo[1])<log.curentTerm()){
                logger.info("writeAndFlush term {}",log.curentTerm());
                ctx.writeAndFlush(log.curentTerm());
            }
        }else if(RaftContext.getStateType().equals(Raft.StateType.LEADER) ){
            String[] voteInfo=msg.toString().split(",");
            if(Integer.valueOf(voteInfo[1])<log.curentTerm()){
                logger.info("writeAndFlush term {}",log.curentTerm());
                ctx.writeAndFlush(log.curentTerm());
            }
        } else if (msg.toString().indexOf("heartBeat") != -1) {
            logger.info("leader heartBeat。。。");
            if (callBack != null) {
                callBack.run(msg);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("channelActive");
    }
}
