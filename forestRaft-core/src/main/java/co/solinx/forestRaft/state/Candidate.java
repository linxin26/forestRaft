package co.solinx.forestRaft.state;

import co.solinx.forestRaft.DeadlineTimer;
import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftClient;
import co.solinx.forestRaft.RaftContext;
import co.solinx.forestRaft.log.RaftLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/28.
 */
public class Candidate implements State{

    private Logger logger= LoggerFactory.getLogger(Candidate.class);
    private RaftClient client;
    private RaftLog log;

    public Candidate(RaftLog log,RaftClient client) {
        this.client = client;
        this.log=log;
    }

    public void init(RaftContext context) {
        logger.info("candidate");

        sendVoteRequest();
    }

    public void sendVoteRequest(){
        logger.info("vote {}", log.curentTerm());

        DeadlineTimer timer=new DeadlineTimer(Raft.StateType.CANDIDATE);

        timer.start(() -> {
            log.newTerm();
            client.voteRequest(log);
        });

    }

}
