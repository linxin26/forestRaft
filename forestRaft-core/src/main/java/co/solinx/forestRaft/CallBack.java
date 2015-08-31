package co.solinx.forestRaft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/31.
 */
public class CallBack {

    Logger logger= LoggerFactory.getLogger(CallBack.class);

    ICallBack callBack;

    public void setCallBack(ICallBack call){
        this.callBack=call;
    }


    public void run(Object msg){
        callBack.run();
        logger.info("result {}",msg);
    }

}
