package co.solinx.forestRaft.state;

import co.solinx.forestRaft.CallBack;
import co.solinx.forestRaft.DeadlineTimer;
import co.solinx.forestRaft.RaftClient;
import co.solinx.forestRaft.RaftContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
;

/**
 * leader状态
 * Created by linx on 2015/8/28.
 */
public class Leader implements State {

    private final Logger logger = LoggerFactory.getLogger(Leader.class);

    private DeadlineTimer timer;
    private long timeout = 1500;
    private RaftContext ctx;

    public Leader() {
        timer = new DeadlineTimer(timeout);
    }

    public void init(RaftContext context) {
        this.ctx = context;
        logger.info("leader---------------");

        CallBack callBack = new CallBack();
        callBack.setCallBack(() -> {

        }, null, null);
        ctx.getClient().open(callBack);


        heartbeat();

    }


    public void heartbeat() {

        timer.start(() -> {
            ctx.getClient().heartBeat();
        });


    }

}
