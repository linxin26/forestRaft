package co.solinx.forestRaft;

import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.state.DefaultStateFactory;
import co.solinx.forestRaft.state.Start;
import com.sun.corba.se.spi.activation.Server;

/**
 * Created by linx on 2015/8/28.
 */
public class Test {

    public static void main(String[] args){
        String[] server=new String[]{"127.0.0.1:21081","127.0.0.1:21082","127.0.0.1:21083"};
//        String[] server=new String[]{"127.0.0.1:21082","127.0.0.1:21081","127.0.0.1:21083"};
        RaftContext start=new RaftContext("server1",new DefaultStateFactory(new RaftLog()),server);
        start.init();
    }
}
