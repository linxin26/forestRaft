package co.solinx.forestRaft.state;

import co.solinx.forestRaft.CallBack;
import co.solinx.forestRaft.DeadlineTimer;
import co.solinx.forestRaft.RaftClient;
import co.solinx.forestRaft.RaftContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 ;

/**
 * Created by linx on 2015/8/28.
 */
public class Leader implements State{

    Logger logger= LoggerFactory.getLogger(Leader.class);

    private RaftClient client;
    private DeadlineTimer timer;
    private long timeout=1500;


    public Leader(RaftClient client) {
        this.client=client;
        timer=new DeadlineTimer(timeout);
    }

    public void init(RaftContext context) {
        logger.info("leader---------------");
        heartbeat();
    }


    public void heartbeat(){
        CallBack callBack=new CallBack();
        callBack.setCallBack(() -> {

        }, null, null);
        client.open(callBack);
        timer.start(() -> {
            client.heartBeat();
        });


    }

}
