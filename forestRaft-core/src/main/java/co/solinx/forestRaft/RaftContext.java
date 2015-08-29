package co.solinx.forestRaft;

import co.solinx.forestRaft.listener.StateTransitionListener;
import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.state.State;
import co.solinx.forestRaft.state.StateFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by linx on 2015/8/28.
 */
public class RaftContext implements Raft{

    private RaftLog log=new RaftLog();
    private State state;
    private StateType stateType;
    private String name;
   private  StateFactory stateFactory;
    private Set<StateTransitionListener> listeners=new ConcurrentSkipListSet<>();
    private String[] servers;

    public RaftContext(String name, StateFactory stateFactory,String[] server) {
        this.name = name;
        this.stateFactory = stateFactory;
        servers=server;
    }

    public void init() {
        setState(StateType.START,null);
    }

    public void setState(StateType stateType,RaftClient client) {
        this.stateType = stateType;
        this. state= stateFactory.makeState(stateType,client);
        state.init(this);
    }

    public String[] getServers() {
        return servers;
    }

    public String getName() {
        return name;
    }
}
