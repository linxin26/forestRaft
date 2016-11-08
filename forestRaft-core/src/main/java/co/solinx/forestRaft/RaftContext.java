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

    private State state;
    private static StateType stateType;
    private String name;
   private  StateFactory stateFactory;
    private Set<StateTransitionListener> listeners=new ConcurrentSkipListSet<>();
    private String[] servers;
    private RaftLog log;

    public RaftContext(String name, StateFactory stateFactory,String[] server) {
        this.name = name;
        this.stateFactory = stateFactory;
        servers=server;
        log =new RaftLog(name);
    }

    public void init() {
        setState(StateType.START,null, null);
    }

    public void setState(StateType stateType, RaftClient client, DeadlineTimer timer) {
        this.stateType = stateType;
        this. state= stateFactory.makeState(stateType,client,timer,log);
        state.init(this);
    }

    public String[] getServers() {
        return servers;
    }

    public String getName() {
        return name;
    }

    public static StateType getStateType(){
        return stateType;
    }
}
