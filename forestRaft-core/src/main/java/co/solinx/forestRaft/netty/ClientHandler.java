package co.solinx.forestRaft.netty;

import co.solinx.forestRaft.CallBack;
import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by lin8x_000 on 2015-08-30.
 */
public class ClientHandler  extends ChannelInboundHandlerAdapter {

    Logger logger= LoggerFactory.getLogger(ClientHandler.class);
    private static String result;
    private static HashMap<String,String> resultMap=new HashMap<>();
    private static boolean isLeader=false;
    private static CallBack callBack;

    public ClientHandler(CallBack callBack) {
        this.callBack=callBack;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("{} {}",msg,ctx.channel().remoteAddress());
        if(msg.toString().indexOf("ok")!=-1) {
            resultMap.put(ctx.channel().remoteAddress().toString().split(":")[1], (String) msg);
            if(callBack!=null)
              callBack.run(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    public static String getResult() {
        return result;
    }

    public static HashMap<String, String> getResultMap() {
        return resultMap;
    }

    public static boolean isLeader() {
        return isLeader;
    }
}
