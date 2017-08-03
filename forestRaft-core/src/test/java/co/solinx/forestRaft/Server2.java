package co.solinx.forestRaft;

import co.solinx.forestRaft.state.DefaultStateFactory;

/**
 * @author linxin
 * @version v1.0
 *          Copyright (c) 2015 by solinx
 * @date 2017/8/3.
 */
public class Server2 {


    public static void main(String[] args) {
        String[] server=new String[]{"127.0.0.1:21082","127.0.0.1:21081","127.0.0.1:21083"};
//        String[] server=new String[]{"127.0.0.1:21083","127.0.0.1:21081","127.0.0.1:21082"};

        RaftContext start=new RaftContext("server1",server);
        start.start();
    }
}
