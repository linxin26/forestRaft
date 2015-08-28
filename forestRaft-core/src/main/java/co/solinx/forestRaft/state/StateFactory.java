package co.solinx.forestRaft.state;

import co.solinx.forestRaft.Raft;

/**
 * Created by linx on 2015/8/28.
 */
public interface StateFactory {

    State makeState(Raft.StateType stateType);

    void changeState();

}
