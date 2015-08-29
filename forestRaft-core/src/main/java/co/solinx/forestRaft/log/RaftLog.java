package co.solinx.forestRaft.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/28.
 */
public class RaftLog {

    Logger loggerFactory= LoggerFactory.getLogger(RaftLog.class);
    private long term;

    public void load(){
        loggerFactory.info("load init ");

        term=0;

    }

    public long curentTerm(){
        return term;
    }

    public long newTerm(){
        return term++;
    }

}
