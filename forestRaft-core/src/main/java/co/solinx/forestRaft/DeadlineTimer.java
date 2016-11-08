package co.solinx.forestRaft;

import com.google.common.base.Optional;
import com.sun.media.jfxmediaimpl.MediaDisposer;
import org.jetlang.core.Disposable;
import org.jetlang.core.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by lin8x_000 on 2015-08-29.
 */
public class DeadlineTimer {

    Logger logger= LoggerFactory.getLogger(DeadlineTimer.class);

    private ScheduledExecutorService timer;
    private  Runnable action;
    private  long timeout;
    private boolean started = false;
    private Runnable scheduler;
    private Raft.StateType state;

    public  DeadlineTimer( long timeout) {
        timer= Executors.newSingleThreadScheduledExecutor();
        this.timeout = timeout;
    }


    public DeadlineTimer(Raft.StateType stateType) {
         state=stateType;

    }

    public void start(Runnable runnable){
        logger.debug("timer start....");
        this.scheduler=runnable;
        timer.scheduleAtFixedRate(runnable,15000,15000, TimeUnit.MILLISECONDS);
        logger.debug("timer start....end ");
    }

//    public void start(Runnable runnable){
////        scheduler.scheduleAtFixedRate(runnable,2000,10000+new Random().nextInt(2000),TimeUnit.MILLISECONDS);
//        scheduler.scheduleAtFixedRate(runnable,5000+new Random().nextInt(3000),8000+new Random().nextInt(3000),TimeUnit.MILLISECONDS);
//    }

    public void cancel(){
        logger.debug("timer shutdown");
        timer.shutdown();
    }

    public void reset(){
        int delay=20000+new Random(10000).nextInt(10000);
        int period= 20000+new Random(10000).nextInt(10000);
        logger.info(" delay {} period {}  timeout {}", delay, period,timeout);
        timer.shutdown();
        timer=Executors.newSingleThreadScheduledExecutor();
        timer.scheduleWithFixedDelay(scheduler, timeout, timeout, TimeUnit.MILLISECONDS);
        logger.info("follower重置时间");
    }

}
