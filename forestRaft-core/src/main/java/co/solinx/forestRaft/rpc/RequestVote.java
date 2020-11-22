package co.solinx.forestRaft.rpc;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import java.io.Serializable;

@ProtobufClass
public class RequestVote{

    public RequestVote() {
    }

    private long term;
    private String candidateId;
    private int lastLogIndex = 0;
    private int lastLogTerm = 0;

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public int getLastLogIndex() {
        return lastLogIndex;
    }

    public void setLastLogIndex(int lastLogIndex) {
        this.lastLogIndex = lastLogIndex;
    }

    public int getLastLogTerm() {
        return lastLogTerm;
    }

    public void setLastLogTerm(int lastLogTerm) {
        this.lastLogTerm = lastLogTerm;
    }
}
