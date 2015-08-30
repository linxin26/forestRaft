package co.solinx.forestRaft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by lin8x_000 on 2015-08-29.
 */
public class RaftServer {

    Logger logger= LoggerFactory.getLogger(RaftServer.class);

    ServerSocket server;
    private String name;

    public RaftServer(String name,int port, String address) {
        try {
            logger.info(address+" "+port);
            this.name=name;
            InetSocketAddress inet = new InetSocketAddress(address, port);
            server = new ServerSocket();
            server.bind(inet);

            while (true) {
                Socket socket = server.accept();

                DataInputStream inputStream=new DataInputStream(socket.getInputStream());
                DataOutputStream output=new DataOutputStream(socket.getOutputStream());
                byte flag=inputStream.readByte();
                System.out.println("receive flag " + flag + " " + socket.getRemoteSocketAddress());
                if(flag==0) {
                    logger.info(" host {}  {}  current state {}",socket.getRemoteSocketAddress(),name,inputStream.readUTF());
                    output.writeUTF("follower");
                }else if(flag==1){
                    logger.info("current server {} receive vote {} request  term {} ",name,socket.getRemoteSocketAddress(),inputStream.readUTF());
                    output.writeUTF("Ok");
                }
//                inputStream.close();
//                socket.close();
            }

        } catch (IOException e) {
            logger.error(e.getMessage(),e.getCause());
        }
    }
}
