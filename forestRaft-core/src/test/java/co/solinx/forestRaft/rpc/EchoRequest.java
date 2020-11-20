package co.solinx.forestRaft.rpc;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * @author linxin
 * @version v1.0
 * @date 2020/11/20.
 */
@ProtobufClass
public class EchoRequest {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}