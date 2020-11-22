package co.solinx.forestRaft.rpc;


import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import java.io.Serializable;

@ProtobufClass
public class RequestVoteResponse implements Serializable {
    private long term;
    private boolean voteGranted;

    public RequestVoteResponse() {
    }

    public RequestVoteResponse(int term, boolean voteGranted) {
        this.term = term;
        this.voteGranted = voteGranted;
    }

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public boolean isVoteGranted() {
        return voteGranted;
    }

    public void setVoteGranted(boolean voteGranted) {
        this.voteGranted = voteGranted;
    }
}
