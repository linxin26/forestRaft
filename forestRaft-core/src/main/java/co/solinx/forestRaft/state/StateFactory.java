package co.solinx.forestRaft.state;

import co.solinx.forestRaft.DeadlineTimer;
import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftClient;

/**
 * Created by linx on 2015/8/28.
 */
public interface StateFactory {

    State makeState(Raft.StateType stateType, RaftClient client, DeadlineTimer timer);

    void changeState();

}
