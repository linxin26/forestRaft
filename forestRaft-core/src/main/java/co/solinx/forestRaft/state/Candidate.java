package co.solinx.forestRaft.state;

import co.solinx.forestRaft.*;
import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.netty.ClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/28.
 */
public class Candidate implements State {

    RaftContext cxt;
    private Logger logger = LoggerFactory.getLogger(Candidate.class);
    private RaftClient client;
    private RaftLog log;
    private DeadlineTimer timer;

    public Candidate(RaftLog log, RaftClient client, DeadlineTimer timer) {
        this.client = client;
        this.log = log;
        this.timer=timer;
    }

    public void init(RaftContext context) {
        logger.info("candidate init");
        this.cxt = context;
        sendVoteRequest();
    }

    public void sendVoteRequest() {
        logger.info("开始投票 vote {}", log.curentTerm());
        DeadlineTimer timer = new DeadlineTimer(Raft.StateType.CANDIDATE);
        CallBack callback=new CallBack();
        callback.setCallBack(() -> {
            logger.info(" callback {}", ClientHandler.getResultMap());
//            if (ClientHandler.getResultMap().size() == (cxt.getServers().length - 1)) {
                logger.info("已选出 Leader ");
//                timer.cancel();
               timer.cancel();
                cxt.setState(Raft.StateType.LEADER, client, timer);
//            }
        }, cxt, timer);
            try {
                client.open(callback);
                log.newTerm();
                client.voteRequest(log);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

}
