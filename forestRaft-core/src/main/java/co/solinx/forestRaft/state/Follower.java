package co.solinx.forestRaft.state;

import co.solinx.forestRaft.*;
import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.netty.NettyServer;
import org.jetlang.core.Scheduler;
import org.jetlang.core.SchedulerImpl;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.FiberStub;
import org.jetlang.fibers.PoolFiberFactory;
import org.jetlang.fibers.ThreadFiber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by linx on 2015/8/28.
 */
public class Follower implements State {

    private Logger logger = LoggerFactory.getLogger(Follower.class);

    private RaftContext cxt;
    private RaftClient client;
    private RaftLog log;
    private DeadlineTimer timer;
    private Timer timers;
    //    private long timeout=20000+new Random(10000).nextInt(10000);
    int timeout = new Random().nextInt(35000) % (35000 - 23000 + 1) + 23000 + (new Random().nextInt(7000 - 4000 + 1) + 4000);

    public Follower(RaftLog log) {
        this.log = log;
    }

    public void init(RaftContext context) {
        cxt = context;
        client = new RaftClient(cxt.getServers(), cxt.getName());
        timer = new DeadlineTimer(timeout);
        timer();
        startServer();

    }

    public void timer() {
        timer.start(new TimerTask() {
            @Override
            public void run() {
                logger.info("follower 切换为 candidate");
                cxt.setState(Raft.StateType.CANDIDATE, client, timer);
                timer.cancel();
            }
        });
    }

    public void startServer() {
        CallBack callBack = new CallBack();
        callBack.setCallBack(() -> {
            //接收到投票请求后重置定时器
            timer.reset();
            logger.info("当前Term {}", log.curentTerm());
        }, null, null);

        Thread thread = new Thread(() -> {
            String serverAdd = cxt.getServers()[0];
            String add = serverAdd.toString().split(":")[0];
            int port = Integer.parseInt(serverAdd.toString().split(":")[1]);

            NettyServer server = new NettyServer(log);
            server.open(add, port, callBack);
        });
        logger.info("server 启动");
        thread.start();
    }
}
