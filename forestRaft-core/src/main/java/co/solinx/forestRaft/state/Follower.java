package co.solinx.forestRaft.state;

import co.solinx.forestRaft.*;
import co.solinx.forestRaft.netty.ClientHandler;
import co.solinx.forestRaft.netty.NettyServer;
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
        System.out.println("timer  ---");
        DeadlineTimer timer=new DeadlineTimer(Raft.StateType.FOLLOWER);
        timer.start(() -> {
            try {
                requestNote();
                String result =ClientHandler.getResult();
                if (result != null) {
                    System.out.println(result);
                    if ("follower".equals(result)) {
                        timer.cancel();
                        context.setState(Raft.StateType.CANDIDATE, client);
                    }




                }
            }catch (Exception e){
                e.printStackTrace();
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
                NettyServer server =new NettyServer();
                server.open(add,port);
//                RaftServer server=new RaftServer(cxt.getName(),port,add);
            }
        });
        thread.start();
    }

    public String requestNote(){
        System.out.println("requestNote");
        client.open();
        String result= client.request(Raft.StateType.FOLLOWER);
        return result;
    }

}
