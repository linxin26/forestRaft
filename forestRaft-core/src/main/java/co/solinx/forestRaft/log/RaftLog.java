package co.solinx.forestRaft.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/28.
 */
public class RaftLog {

    Logger loggerFactory= LoggerFactory.getLogger(RaftLog.class);
    private long term;
    private String name;


    public RaftLog(String name) {
        this.name = name;
        this.term=0;
    }

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

    public void setTerm(long term){
        this.term=term;
    }

}
