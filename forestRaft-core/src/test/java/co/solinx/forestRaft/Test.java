package co.solinx.forestRaft;

import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.state.DefaultStateFactory;
import co.solinx.forestRaft.state.Start;

/**
 * Created by linx on 2015/8/28.
 */
public class Test {

    public static void main(String[] args){
        RaftContext start=new RaftContext("server1",new DefaultStateFactory(new RaftLog()));
        start.init();
    }
}
