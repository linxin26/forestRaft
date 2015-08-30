package co.solinx.forestRaft;

import co.solinx.forestRaft.log.RaftLog;
import co.solinx.forestRaft.netty.NettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    public void open(){
        socket=new Socket[serverNote.length-1];
        client=new NettyClient[serverNote.length-1];
        for (int i = 0; i < serverNote.length-1; i++) {
            String addre=serverNote[i+1].split(":")[0];
            int port= Integer.parseInt(serverNote[i+1].split(":")[1]);
//            System.out.println("-------------  "+port);
            //                socket[i]=new Socket(addre,port);
            client[i]=new NettyClient();
            client[i].open(addre,port);
            client[i].connect();
        }
    }

    public void voteRequest(RaftLog log){
        logger.info("vote request term {}", log.curentTerm());
        for (int i = 0; i <client.length; i++) {
            if(client[i]!=null){
            this.open();

//            output[i] = new DataOutputStream(socket[i].getOutputStream());
//            input[i]=new DataInputStream(socket[i].getInputStream());
//            output[i].writeByte(1);
//            output[i].writeUTF(String.valueOf(log.curentTerm()));
                client[i].send("1"+","+String.valueOf(log.curentTerm()));
//            logger.info("current server {}  remote {}  vote result {}", name, socket[i].getRemoteSocketAddress(), input[i].readUTF());
//            input[i].close();
//            output[i].close();
            }
        }
    }

    public String request(Raft.StateType stateType){
        String result=null;
    for (int i = 0; i <client.length; i++) {
        if(client[i]!=null){
            client[i].send("0"+","+stateType.toString());
        }}
        return  result;
    }
//    public String request(Raft.StateType stateType){
//
//        String result=null;
//        input=new DataInputStream[socket.length];
//        output=new DataOutputStream[socket.length];
//        for (int i = 0; i < socket.length; i++) {
//            System.out.println(i);
//        try {
//            if(socket[i]!=null&&socket[i].isConnected()) {
//                input[i] = new DataInputStream(socket[i].getInputStream());
//                output[i] = new DataOutputStream(socket[i].getOutputStream());
//                output[i].writeUTF("0");
//                output[i].writeUTF(String.valueOf(stateType));
//                System.out.println("read");
//                result = input[i].readUTF();
//                System.out.println("read data " +result);
//                logger.info( "{} current state {}",name,result);
//                System.out.println(socket[i].getRemoteSocketAddress());
//                output[i].close();
//                input[i].close();
//            }else{
//                logger.info("not connect");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        }
//        return result;
//    }


}
