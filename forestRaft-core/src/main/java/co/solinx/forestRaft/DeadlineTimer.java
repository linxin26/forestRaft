package co.solinx.forestRaft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lin8x_000 on 2015-08-29.
 */
public class DeadlineTimer {

    Logger logger= LoggerFactory.getLogger(DeadlineTimer.class);
    private ScheduledThreadPoolExecutor scheduler=new ScheduledThreadPoolExecutor(2);
    private Raft.StateType state;

    public DeadlineTimer(Raft.StateType stateType) {
         state=stateType;

    }

    public void start(){
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info(state + " time out ");
            }
        }, 2000, 2000, TimeUnit.MILLISECONDS);
    }

    public void start(Runnable runnable){
//        scheduler.scheduleAtFixedRate(runnable,2000,10000+new Random().nextInt(2000),TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(runnable,5000,10000,TimeUnit.MILLISECONDS);
    }

    public void cancel(){
        scheduler.shutdown();
    }

}
