package co.solinx.forestRaft.state;

import co.solinx.forestRaft.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/28.
 */
public class Follower implements State{

    private Logger logger= LoggerFactory.getLogger(Follower.class);

    private RaftContext cxt;
    private RaftClient client;

    public void init(RaftContext context) {
        cxt=context;
        logger.info("follower");
         client=new RaftClient(cxt.getServers(),cxt.getName());
        startServer();
        DeadlineTimer timer=new DeadlineTimer(Raft.StateType.FOLLOWER);
        timer.start(() -> {
            String result = requestNote();
            if (result != null) {
                if ("follower".equals(result)) {

                    timer.cancel();
                    context.setState(Raft.StateType.CANDIDATE,client);
                }
            }
        });
    }
    public void startServer(){

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("startServer");
                String serverAdd= cxt.getServers()[0];
                String add=serverAdd.toString().split(":")[0];
                int port = Integer.parseInt(serverAdd.toString().split(":")[1]);
                RaftServer server=new RaftServer(cxt.getName(),port,add);
            }
        });
        thread.start();
    }

    public String requestNote(){

        client.open();
        String result= client.request(Raft.StateType.FOLLOWER);
        return result;
    }

}
