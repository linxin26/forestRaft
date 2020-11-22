package co.solinx.forestRaft.rpc;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

@ProtobufClass
public class AppendEntriesResponse {
    private String rpcMessageId;
    private int term;
    private boolean success;

    public AppendEntriesResponse() {
    }

    public AppendEntriesResponse(String rpcMessageId, int term, boolean success) {
        this.rpcMessageId = rpcMessageId;
        this.term = term;
        this.success = success;
    }

    public String getRpcMessageId() {
        return rpcMessageId;
    }

    public int getTerm() {
        return term;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setRpcMessageId(String rpcMessageId) {
        this.rpcMessageId = rpcMessageId;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
