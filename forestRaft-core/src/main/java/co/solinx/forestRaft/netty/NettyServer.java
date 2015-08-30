package co.solinx.forestRaft.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


/**
 * Created by lin8x_000 on 2015-08-30.
 */
public class NettyServer {

    Logger logger= LoggerFactory.getLogger(NettyServer.class);

    private ServerBootstrap server;

    public void open(String address,int port){
        server=new ServerBootstrap();
        EventLoopGroup boosGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        server.group(boosGroup, workerGroup);
        server.channel(NioServerSocketChannel.class);
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                sc.pipeline().addLast(new StringDecoder());
                sc.pipeline().addLast(new StringEncoder());
                sc.pipeline().addLast(new ServiceHandler());
            }
        });
        server.bind(new InetSocketAddress(address, port));
        logger.info("server staart bind by {}",port);
    }

}
