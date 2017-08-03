package co.solinx.forestRaft.state;

import co.solinx.forestRaft.DeadlineTimer;
import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftClient;
import co.solinx.forestRaft.log.RaftLog;

/**
 * Created by linx on 2015/8/28.
 */
public class DefaultStateFactory implements StateFactory{

    public DefaultStateFactory() {
    }

    public State makeState(Raft.StateType stateType, DeadlineTimer timer) {
        State state = null;
        switch(stateType){
            case START:
                state=new Start();
                break;
            case FOLLOWER:
                state=new Follower();
                break;
            case LEADER:
                state=new Leader();
                break;
            case CANDIDATE:
                state=new Candidate(new DeadlineTimer(2000));
                break;
        }
        return state;
    }

    public void changeState() {

    }
}
