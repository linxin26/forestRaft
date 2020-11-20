package co.solinx.forestRaft.rpc;

import com.baidu.brpc.protocol.BrpcMeta;

/**
 * @author linxin
 * @version v1.0
 * @date 2020/11/20.
 */
public interface EchoService {
    /**
     * brpc/sofa：
     * serviceName默认是包名 + 类名，methodName是proto文件Service内对应方法名，
     * hulu/public_pbrpc：
     * serviceName默认是类名，不需要加包名，methodName是proto文件Service内对应方法index，默认从0开始。
     */
//    @BrpcMeta(serviceName = "example.EchoService", methodName = "Echo")
//    @BrpcMeta(serviceName = "EchoService", methodName = "0")
//    Echo.EchoResponse echo(Echo.EchoRequest request);
    @BrpcMeta(serviceName = "EchoService", methodName = "0")
    public void echo(String str);

    @BrpcMeta(serviceName = "EchoService", methodName = "Echo")
    EchoResponse echo(EchoRequest request);
}
