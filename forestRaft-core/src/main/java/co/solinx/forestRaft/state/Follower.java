package co.solinx.forestRaft.state;

import co.solinx.forestRaft.*;
import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.netty.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/28.
 */
public class Follower implements State{

    private Logger logger= LoggerFactory.getLogger(Follower.class);

    private RaftContext cxt;
    private RaftClient client;
    private RaftLog log;
    private DeadlineTimer timer;

    public void init(RaftContext context) {
        log=new RaftLog();
        cxt=context;
        this.timer =new DeadlineTimer(Raft.StateType.FOLLOWER);
         client=new RaftClient(cxt.getServers(),cxt.getName());
        startServer();
        timer();
    }

    public void timer(){
        timer.start(() -> {
            logger.info("follower 切换为 candidate");
            cxt.setState(Raft.StateType.CANDIDATE, client, timer);
        });
    }

    public void startServer(){
        CallBack callBack=new CallBack();
        callBack.setCallBack(()->{
            timer.restart(()->{logger.info("follower重置时间");
                cxt.setState(Raft.StateType.CANDIDATE, client, timer);
            });
        },null,null);

        Thread thread=new Thread(()-> {
            String serverAdd= cxt.getServers()[0];
            String add=serverAdd.toString().split(":")[0];
            int port = Integer.parseInt(serverAdd.toString().split(":")[1]);

            NettyServer server =new NettyServer();
            server.open(add,port,callBack);
        });
        logger.info("server 启动");
        thread.start();
    }
}
