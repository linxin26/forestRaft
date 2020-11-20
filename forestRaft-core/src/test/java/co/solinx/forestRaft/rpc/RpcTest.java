package co.solinx.forestRaft.rpc;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.client.channel.Endpoint;
import com.baidu.brpc.protocol.Options;
import com.baidu.brpc.server.RpcServerOptions;

/**
 * @author linxin
 * @version v1.0
 * Copyright (c) 2015 by e_trans
 * @date 2020/11/20.
 */
public class RpcTest {
    public static void main(String[] args) {
        Endpoint endpoint=new Endpoint();
        endpoint.setIp("192.168.5.118");
        endpoint.setPort(8080);
        RpcClientOptions options = new RpcClientOptions();
//        options.setProtocolType(Options.ProtocolType.PROTOCOL_UNKNOWN_VALUE);

        RpcClient rpcClient=new RpcClient(endpoint,options);
        EchoService  raftConsensusServiceAsync = BrpcProxy.getProxy(rpcClient, EchoService.class);
        raftConsensusServiceAsync.echo("test");
    }
}
