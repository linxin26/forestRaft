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
         client=new RaftClient(cxt.getServers(),cxt.getName());
        startServer();
        this.getNodeState(cxt);
    }

    public void getNodeState(RaftContext cxt){
        DeadlineTimer timer=new DeadlineTimer(Raft.StateType.FOLLOWER);
        CallBack callBack=new CallBack();
        callBack.setCallBack(()->{
            String result =ClientHandler.getResult();
            if (result != null) {
                System.out.println(result);
                if ("follower".equals(result)) {
                    timer.cancel();
                    cxt.setState(Raft.StateType.CANDIDATE, client);
                }
            }
        });

        timer.start(() -> {
            try {
                requestNote(callBack);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void startServer(){
        Thread thread=new Thread(()-> {
                String serverAdd= cxt.getServers()[0];
                String add=serverAdd.toString().split(":")[0];
                int port = Integer.parseInt(serverAdd.toString().split(":")[1]);
                NettyServer server =new NettyServer();
                server.open(add,port);
        });
        thread.start();
    }

    public void requestNote(CallBack callBack){
        client.open(callBack);
    client.request(Raft.StateType.FOLLOWER);
    }

}
