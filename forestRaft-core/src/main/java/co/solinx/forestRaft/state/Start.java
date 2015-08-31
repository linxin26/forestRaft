package co.solinx.forestRaft.state;

import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftContext;
import co.solinx.forestRaft.log.RaftLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by linx on 2015/8/28.
 */
public class Start implements State{

    Logger logger= LoggerFactory.getLogger(Start.class);

    RaftLog log;
    RaftContext cxt;

    public Start(RaftLog log){
        this.log=log;
        MDC.put("state", Raft.StateType.START.name());
        log.load();

    }

    public void init(RaftContext context) {
        cxt=context;
        context.setState(Raft.StateType.FOLLOWER,null, null);
    }


}
