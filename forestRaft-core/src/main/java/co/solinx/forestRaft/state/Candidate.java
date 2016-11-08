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
    private RaftClient client;
    private RaftLog log;
    private DeadlineTimer timer;

    public Candidate(RaftLog log, RaftClient client, DeadlineTimer timer) {
        this.client = client;
        this.log = log;
        this.timer = timer;
        this.initClient();
    }

    public void initClient(){
        CallBack callback = new CallBack();
        callback.setCallBack(() -> {
            logger.info(" callback {}", ClientHandler.getResultMap());
            String term = ClientHandler.getResultMap().get(ClientHandler.getResultMap().keySet().toArray()[0]);

            if (term.equals("ok")) {
                logger.info("当选为Leader ");
                cxt.setState(Raft.StateType.LEADER, client, timer);
            } else {
                logger.info("已选出 Leader ");
                cxt.setState(Raft.StateType.FOLLOWER, client, timer);
            }
            timer.cancel();

        }, cxt, timer);
        client.open(callback);
    }

    public void init(RaftContext context) {
        logger.info("candidate init");
        this.cxt = context;
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void sendVoteRequest() {
        logger.info("开始投票 当前Term {}", log.curentTerm());

        try {

            log.newTerm();
            client.voteRequest(log);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
