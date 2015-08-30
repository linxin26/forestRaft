package co.solinx.forestRaft.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by lin8x_000 on 2015-08-30.
 */
public class NettyClient {

    private Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private Bootstrap bootstrap;
    private NioEventLoopGroup eventLoopGroup;
    private String address;
    private int port;
    private Channel channel;

    public void open(String address, int port) {
        this.address = address;
        this.port = port;
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                sc.pipeline().addLast(new StringEncoder());
                sc.pipeline().addLast(new StringDecoder());
                sc.pipeline().addLast(new ClientHandler());
            }
        });

    }

    public void connect() {
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(address, port));
        while (true) {
            if (future.isDone()) {
                break;
            }
        }
        logger.info("{} end-------done:{} {} ", port, future.isDone(), future.isSuccess());

        this.channel = future.channel();

    }

    public void send(Object object) {
        this.channel.writeAndFlush(object);
    }

}
