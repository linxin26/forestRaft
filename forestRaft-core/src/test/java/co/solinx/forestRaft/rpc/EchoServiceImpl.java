package co.solinx.forestRaft.rpc;

import com.baidu.brpc.protocol.BrpcMeta;

/**
 * @author linxin
 * @version v1.0
 * @date 2020/11/20.
 */
public class EchoServiceImpl implements EchoService {
    @Override
    public void echo(String str) {
        System.out.println(str);
    }

    @Override
    public EchoResponse echo(EchoRequest request) {
        System.out.println(request);
        return new EchoResponse();
    }
    /**
     * brpc/sofa：
     * serviceName默认是包名 + 类名，methodName是proto文件Service内对应方法名，
     * hulu/public_pbrpc：
     * serviceName默认是类名，不需要加包名，methodName是proto文件Service内对应方法index，默认从0开始。
     */
//    @BrpcMeta(serviceName = "example.EchoService", methodName = "Echo")
//    @BrpcMeta(serviceName = "EchoService", methodName = "0")
//    Echo.EchoResponse echo(Echo.EchoRequest request);
}
