package co.solinx.forestRaft.netty;

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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg.toString().indexOf("leader")==-1) {
            String add = ctx.channel().remoteAddress().toString();
            logger.info("{} {} ", add, msg);
            resultMap.put(add.split(":")[1], (String) msg);
            this.result = (String) msg;
        }else{
            String re= msg.toString().split(",")[1];
            isLeader=re.equals("1")?true:false;
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
