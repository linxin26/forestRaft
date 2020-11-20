package co.solinx.forestRaft.rpc;

import com.baidu.brpc.protocol.Options;
import com.baidu.brpc.server.RpcServer;
import com.baidu.brpc.server.RpcServerOptions;

/**
 * @author linxin
 * @version v1.0
 * Copyright (c) 2015 by e_trans
 * @date 2020/11/20.
 */
public class RpcServerTest {
    public static void main(String[] args) {
        RpcServerOptions options = new RpcServerOptions();
//        options.setProtocolType(Options.ProtocolType.PROTOCOL_NSHEAD_PROTOBUF_VALUE);
//         options.setProtocolType(Options.ProtocolType.PROTOCOL_NSHEAD_JSON_VALUE);
        options.setEncoding("gbk");
        RpcServer rpcServer = new RpcServer("192.168.5.118",8080);
        rpcServer.registerService(new EchoServiceImpl());
        rpcServer.start();
        // make server keep running
        synchronized (RpcServerTest.class) {
            try {
                RpcServerTest.class.wait();
            } catch (Throwable e) {
                // ignore
            }
        }
    }
}
