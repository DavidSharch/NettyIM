package com.im.tcp.server;

import com.im.tcp.codec.MessageDecoder;
import com.im.tcp.config.IMServerConfig;
import com.im.tcp.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author : sharch
 * @create 2023/9/25 20:27
 */
@Slf4j
public class ImServer {
    IMServerConfig.TcpConfig config;
    EventLoopGroup mainGroup;
    EventLoopGroup subGroup;
    ServerBootstrap server;

    public ImServer(IMServerConfig.TcpConfig config) {
        // config.getBossThreadSize()
        mainGroup = new NioEventLoopGroup();
        // config.getWorkThreadSize()
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup,subGroup)
                .channel(NioServerSocketChannel.class)
                // 服务端可连接队列大小
                .option(ChannelOption.SO_BACKLOG, 10240)
                // 参数表示允许重复使用本地地址和端口
                .option(ChannelOption.SO_REUSEADDR, true)
                // 是否禁用Nagle算法 简单点说是否批量发送数据 true关闭 false开启。 开启的话可以减少一定的网络开销，但影响消息实时性
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 保活开关2h没有数据服务端会发送心跳包
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageDecoder());
//                        ch.pipeline().addLast(new MessageEncoder());
//                        ch.pipeline().addLast(new HeartBeatHandler(config.getHeartBeatTime()));
                        //ch.pipeline().addLast(new NettyServerHandler(config.getBrokerId(),config.getLogicUrl()));
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });
    }

    public void start(){
        this.server.bind(this.config.getTcpPort());
    }
}
