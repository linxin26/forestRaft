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

    public State makeState(Raft.StateType stateType, RaftClient client, DeadlineTimer timer,RaftLog log) {
        State state = null;
        switch(stateType){
            case START:
                state=new Start(log);
                break;
            case FOLLOWER:
                state=new Follower(log);
                break;
            case LEADER:
                state=new Leader(client);
                break;
            case CANDIDATE:
                state=new Candidate(log,client,new DeadlineTimer(2000));
                break;
        }
        return state;
    }

    public void changeState() {

    }
}
