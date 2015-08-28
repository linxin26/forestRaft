package co.solinx.forestRaft;

import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.state.State;
import co.solinx.forestRaft.state.StateFactory;

import java.util.Set;

/**
 * Created by linx on 2015/8/28.
 */
public class RaftContext implements Raft{

    private RaftLog log=new RaftLog();
    private State state;
    private StateType stateType;
    private String name;
   private  StateFactory stateFactory;
//    private Set<StateTransitionListener>

    public RaftContext(String name, StateFactory stateFactory) {
        this.name = name;
        this.stateFactory = stateFactory;
    }

    public void init() {
        setState(StateType.START);
    }

    public void setState(StateType stateType) {
        this.stateType = stateType;
        this. state= stateFactory.makeState(stateType);
        state.init(this);
    }
}
