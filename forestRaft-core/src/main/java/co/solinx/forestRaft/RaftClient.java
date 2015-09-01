package co.solinx.forestRaft;

import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.netty.NettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by lin8x_000 on 2015-08-29.
 */
public class RaftClient {

    Logger logger= LoggerFactory.getLogger(RaftClient.class);
    String[] serverNote;
    String name;
    Socket[] socket;
    DataInputStream[] input;
    DataOutputStream[] output;
    NettyClient client[];

    public RaftClient(String[] servers,String name) {
        serverNote=servers;
        this.name=name;


    }

    public void open(CallBack callBack){
        socket=new Socket[serverNote.length-1];
        client=new NettyClient[serverNote.length-1];
        for (int i = 0; i < serverNote.length-1; i++) {
            String addre=serverNote[i+1].split(":")[0];
            int port= Integer.parseInt(serverNote[i+1].split(":")[1]);
            client[i]=new NettyClient();
            client[i].open(addre,port,callBack);
            logger.info("{} {} {}",addre,port,i);
            if(!client[i].connect()){
                client[i]=null;
            }
        }
    }

    public void voteRequest(RaftLog log){
        for (int i = 0; i <client.length; i++) {
            if(client[i]!=null){
                logger.info("client {} vote request term {}",i,log.curentTerm());
//               this.open(null);
                client[i].send("vote"+","+String.valueOf(log.curentTerm()));
            }
        }
    }


    public void heartBeat(){
        for (int i = 0; i < client.length; i++) {
            if(client[i]!=null){
                logger.info("发送心跳。");
                client[i].send("heartBeat");
            }
        }
    }


}
