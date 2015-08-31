package co.solinx.forestRaft.state;

import co.solinx.forestRaft.DeadlineTimer;
import co.solinx.forestRaft.RaftContext;

/**
 * Created by linx on 2015/8/28.
 */
public interface State {

    void init(RaftContext context);

}
