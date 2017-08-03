package co.solinx.forestRaft.state;

import co.solinx.forestRaft.*;
import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.netty.ClientHandler;
import org.jetlang.fibers.FiberStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * Created by linx on 2015/8/28.
 */
public class Candidate implements State {

    RaftContext cxt;
    private Logger logger = LoggerFactory.getLogger(Candidate.class);
    private RaftLog log;
    private DeadlineTimer timer;

    public Candidate(DeadlineTimer timer) {
        this.timer = timer;
    }

    public void initClient() {
        CallBack callback = new CallBack();
        callback.setCallBack(() -> {
            logger.info(" callback {}", ClientHandler.getResultMap());
            String term = ClientHandler.getResultMap().get(ClientHandler.getResultMap().keySet().toArray()[0]);

            if (term.equals("ok")) {
                logger.info("当选为Leader ");
                cxt.setState(Raft.StateType.LEADER, timer);
            } else {
                logger.info("已选出 Leader ");
                cxt.setState(Raft.StateType.FOLLOWER, timer);
            }
            timer.cancel();

        }, cxt, timer);
        this.cxt.getClient().open(callback);
    }

    public void init(RaftContext context) {
        logger.info("candidate init");
        this.cxt = context;
        this.log = context.getLog();
        this.initClient();
        timer();
    }

    public void timer() {
        try {
            timer.start(new TimerTask() {
                @Override
                public void run() {
                    logger.info("candidate投票。。。");
                    sendVoteRequest();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendVoteRequest() {
        logger.info("开始投票 当前Term {}", cxt.getLog().curentTerm());

        try {

            log.newTerm();
            this.cxt.getClient().voteRequest(log);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
