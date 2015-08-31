package co.solinx.forestRaft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/31.
 */
public class CallBack {

    Logger logger= LoggerFactory.getLogger(CallBack.class);

    private ICallBack callBack;
    private RaftContext cxt;
    private DeadlineTimer timer;

    public void setCallBack(ICallBack call,RaftContext context,DeadlineTimer timer){
        this.callBack=call;
        this.cxt=context;
        this.timer=timer;
    }


    public void run(Object msg){
            callBack.run();

    }

}
