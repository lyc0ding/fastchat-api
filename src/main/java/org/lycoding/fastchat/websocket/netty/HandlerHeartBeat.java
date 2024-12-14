package org.lycoding.fastchat.websocket.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.IdentifierAccessor;

/**
 * @Author: lycoding
 * @Description: 心跳检测
 * @DateTime: 2024/9/12 11:39
 **/
@Slf4j
public class HandlerHeartBeat extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Channel channel= ctx.channel();
        if (evt instanceof IdleStateEvent){
            IdleStateEvent e = (IdleStateEvent)evt;
            if(e.state()== IdleState.READER_IDLE){
                Attribute<String> attr = channel.attr(AttributeKey.valueOf(channel.id().toString()));
                String userId = attr.get();
                log.info("用户：{} 心跳超时",userId);
                ctx.close();
            }else if(e.state()== IdleState.WRITER_IDLE){
                ctx.writeAndFlush("heart");
            }
        }
    }
}
