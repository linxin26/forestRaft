package co.solinx.forestRaft.state;

import co.solinx.forestRaft.DeadlineTimer;
import co.solinx.forestRaft.Raft;
import co.solinx.forestRaft.RaftClient;
import co.solinx.forestRaft.RaftContext;
import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.netty.ClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/28.
 */
public class Candidate implements State{

    private Logger logger= LoggerFactory.getLogger(Candidate.class);
    private RaftClient client;
    private RaftLog log;
    RaftContext cxt;

    public Candidate(RaftLog log,RaftClient client) {
        this.client = client;
        this.log=log;
    }

    public void init(RaftContext context) {
        logger.info("candidate");
        this.cxt=context;
        sendVoteRequest();
    }

    public void sendVoteRequest(){
        logger.info("vote {}", log.curentTerm());

        DeadlineTimer timer=new DeadlineTimer(Raft.StateType.CANDIDATE);

        timer.start(() -> {
            try {
                log.newTerm();
                client.getState();
                boolean done = true;
                logger.info("isLeader {} ",ClientHandler.isLeader());
                if(!ClientHandler.isLeader()) {
                    client.voteRequest(log);
                    logger.info(ClientHandler.getResultMap().toString());
                    for (String temp : ClientHandler.getResultMap().values()) {
                        System.out.println(temp);
                        if (temp.indexOf("Ok") == -1) {
                            System.out.println(done);
                            done = false;
                        }
                    }
                    if(done){
                        cxt.setState(Raft.StateType.LEADER,client);
                    }
                }else{
                    timer.cancel();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

}
