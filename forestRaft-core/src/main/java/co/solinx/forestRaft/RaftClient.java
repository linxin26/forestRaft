package co.solinx.forestRaft;

import co.solinx.forestRaft.log.RaftLog;
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
    Socket socket;
    DataInputStream input;
    DataOutputStream output;

    public RaftClient(String[] servers,String name) {
        serverNote=servers;
        this.name=name;


    }

    public void open(){
        String addre=serverNote[1].split(":")[0];
        int port= Integer.parseInt(serverNote[1].split(":")[1]);

        try {
             socket=new Socket(addre,port);
        } catch (IOException e) {
            logger.error(e.getMessage() + " " + addre + " " + port, e.getCause());
            socket=null;
        }
    }

    public void voteRequest(RaftLog log){
        logger.info("vote request term {}", log.curentTerm());
        try {
            if(socket.isClosed()){
                this.open();
            }
            output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(String.valueOf(log.curentTerm()));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String request(Raft.StateType stateType){

        String result=null;
        try {
            if(socket!=null) {
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(String.valueOf(stateType));

                result = input.readUTF();
                logger.info( "{} current state {}",name,result);
                output.close();
                input.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
