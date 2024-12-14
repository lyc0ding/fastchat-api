package org.lycoding.fastchat.websocket.netty;

import com.mysql.cj.util.StringUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: lycoding
 * @Description: Websocket启动类
 * @DateTime: 2024/9/12 10:19
 **/
@Slf4j
@Component
public class NettyServer {
    private static EventLoopGroup boosEventGroup=new NioEventLoopGroup(1);
    private static EventLoopGroup workEventGroup=new NioEventLoopGroup();

    @Autowired
    private HandlerWebSocket handlerWebSocket;

    @Value("${netty.port}")
    private Integer nettyServerPort;

    @Async //一定要异步启动，不然程序会扛不住或者卡在等待连接
    public void nettyStart(){
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boosEventGroup, workEventGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            /**
                             * 设置处理器
                             */
                            //对Http协议的支持，使用Http编码器、解码器
                            pipeline.addLast(new HttpServerCodec());
                            //聚合解码， httpRequest
                            //保证接收的Http完整性
                            pipeline.addLast(new HttpObjectAggregator(64*1024));
                            //心跳,readerIdleTime 超过规定事件没有读事件自动断开连接
                            pipeline.addLast(new IdleStateHandler(60,0,0, TimeUnit.SECONDS));
                            pipeline.addLast(new HandlerHeartBeat());
                            //将Http协议升级为ws协议，对websocket支持
                            pipeline.addLast(new WebSocketServerProtocolHandler("",null,true,65536,true,true,10000L));
                            pipeline.addLast(handlerWebSocket);
                        }
                    });
            //调用sync方法会阻塞当前线程，直到channel关闭完成
            String nettyServerStr=System.getenv("ws.port");
            if (!(StringUtils.isNullOrEmpty(nettyServerStr))) {
                nettyServerPort = Integer.parseInt(nettyServerStr);
            }
            ChannelFuture channelFuture = serverBootstrap.bind(nettyServerPort).sync();
            log.info("Netty启动成功……服务端口：{}",nettyServerPort);
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            log.warn("netty启动失败……");
            e.printStackTrace();
        }finally {
            //关闭两个事件
            workEventGroup.shutdownGracefully();
            boosEventGroup.shutdownGracefully();
        }
    }
}
