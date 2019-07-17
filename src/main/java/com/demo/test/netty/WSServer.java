package com.demo.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;


@Component
public class WSServer {
    /**
     * 单例，保证内存中只会有一个服务实例
     */
    private static class Singleton{
        private static final WSServer instance;
        static{
                instance = new WSServer();
        }

        public static WSServer getInstance() {
            return instance;
        }
    }

    public WSServer getInstance(){
        return Singleton.getInstance();
    }

    private static final Logger log = LoggerFactory.getLogger(WSServer.class);
    private ServerBootstrap serverBootstrap;

    @PostConstruct   //随spring 启动而启动，不需要shutdown，交给spring来管理
    private void run() {
        try {
            String upServerHost = "localhost";
            int upServerPort = 9000;
            ChannelFuture future = serverBootstrap.bind(InetAddress.getByName(upServerHost), upServerPort).sync();
//            future = serverBootstrap.bind("192.168.0.114",upServerPort).sync();
            log.info("UpServer服务启动在" + upServerHost + ":" + upServerPort + "端口");
//            future.channel().closeFuture().sync();  //会阻塞，所以取消
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WSServer() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new WSServerInitializer());
    }
}
