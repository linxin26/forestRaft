package co.solinx.forestRaft.state;

import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftClient;
import co.solinx.forestRaft.log.RaftLog;

/**
 * Created by linx on 2015/8/28.
 */
public class DefaultStateFactory implements StateFactory{

    RaftLog log;

    public DefaultStateFactory(RaftLog log) {
        this.log=log;
    }

    public State makeState(Raft.StateType stateType,RaftClient client) {
        State state = null;
        switch(stateType){
            case START:
                state=new Start(log);
                break;
            case FOLLOWER:
                state=new Follower();
                break;
            case LEADER:
                state=new Leader();
                break;
            case CANDIDATE:
                state=new Candidate(log,client);
                break;
        }
        return state;
    }

    public void changeState() {

    }
}
