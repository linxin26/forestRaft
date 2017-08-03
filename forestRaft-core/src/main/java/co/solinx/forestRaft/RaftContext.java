package co.solinx.forestRaft;

import co.solinx.forestRaft.listener.StateTransitionListener;
import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.state.DefaultStateFactory;
import co.solinx.forestRaft.state.State;
import co.solinx.forestRaft.state.StateFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * start()——》start State
 * <p>
 * Created by linx on 2015/8/28.
 */
public class RaftContext implements Raft {

    private State state;
    private static StateType stateType;
    private String name;
    private StateFactory stateFactory;
    private Set<StateTransitionListener> listeners = new ConcurrentSkipListSet<>();
    private String[] servers;
    private RaftLog log;
    private RaftClient client;

    public RaftContext(String name, StateFactory stateFactory, String[] server) {
        this.name = name;
        this.stateFactory = stateFactory;
        this.servers = server;
        this.log = new RaftLog(name);
    }

    public RaftContext(String name, String[] servers) {
        this(name, new DefaultStateFactory(), servers);
    }

    public void start() {
        init();
    }

    private void init() {
        setState(StateType.START, null);
        this.client = new RaftClient(servers, name);
    }

    public void setState(StateType stateType, DeadlineTimer timer) {
        this.stateType = stateType;
        this.state = stateFactory.makeState(stateType, timer);
        state.init(this);
    }

    public void setState(StateType stateType) {
        this.setState(stateType, null);
    }

    public String[] getServers() {
        return servers;
    }

    public String getName() {
        return name;
    }

    public static StateType getStateType() {
        return stateType;
    }


    public RaftLog getLog() {
        return log;
    }

    public RaftClient getClient() {
        return client;
    }
}
