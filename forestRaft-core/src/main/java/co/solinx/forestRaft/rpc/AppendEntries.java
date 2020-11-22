package co.solinx.forestRaft.rpc;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import java.util.Collections;
import java.util.List;

@ProtobufClass
public class AppendEntries {
    private String messageId;
    private long term;
    private String leaderId;
    private int prevLogIndex = 0;
    private int prevLogTerm;
//    private List<Entry> entries = Collections.emptyList();
    private int leaderCommit;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public int getPrevLogIndex() {
        return prevLogIndex;
    }

    public void setPrevLogIndex(int prevLogIndex) {
        this.prevLogIndex = prevLogIndex;
    }

    public int getPrevLogTerm() {
        return prevLogTerm;
    }

    public void setPrevLogTerm(int prevLogTerm) {
        this.prevLogTerm = prevLogTerm;
    }

    public int getLeaderCommit() {
        return leaderCommit;
    }

    public void setLeaderCommit(int leaderCommit) {
        this.leaderCommit = leaderCommit;
    }
}
