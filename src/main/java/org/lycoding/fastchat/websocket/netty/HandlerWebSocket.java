package org.lycoding.fastchat.websocket.netty;

import com.mysql.cj.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.lycoding.fastchat.entity.Constants;
import org.lycoding.fastchat.utils.JwtUtils;
import org.lycoding.fastchat.redis.RedisCache;
import org.lycoding.fastchat.websocket.ChannelContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: lycoding
 * @Description: 自定义的webSocket处理
 * @DateTime: 2024/9/12 11:48
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class HandlerWebSocket extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ChannelContextUtils channelContextUtils;


    //通道就绪后调用，一般用于做初始化
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("有新的连接加入……");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //检测是否连接（握手完成）
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete=(WebSocketServerProtocolHandler.HandshakeComplete)evt;
            String url = complete.requestUri();
            String token = getToken(url);  //通过url解析token
            if (StringUtils.isNullOrEmpty(token)){   //token为空，关闭通道，服务器拒绝连接
                ctx.channel().close();
            }
            String value = redisCache.getValue(Constants.TOKEN_STORE + token); //通过token解析出当前登录用户的userId
            String userId ="";
            if (StringUtils.isNullOrEmpty(value)){
                log.warn("无效token：{}",token);
            }else {
                Map<String, Object> map = JwtUtils.parseToken(token);
                userId = map.get("userId").toString();
                log.info("当前连接用户为：{}",userId);
                //通过Channel的Attribute将 channelId与userId绑定到一起
                channelContextUtils.addChannelContext(userId, ctx.channel());
            }
        }
    }

    //连接关闭后（ctx close后）做一些事情
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelContextUtils.removeContext(ctx.channel());
        log.info("用户：{} 已断开连接……");
    }

    //收消息
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Channel channel = channelHandlerContext.channel();
        //通过解析channel id 得到该通道绑定的userId
        Attribute<String> attr = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attr.get();
        String message = textWebSocketFrame.text();
        log.info("收到消息用户：{} 的消息：{}",userId,textWebSocketFrame.text());
        //发送消息到群组
//        channelContextUtils.sendMsgToGroup(message);
    }

    /**
     * 处理token信息
     */
    private String getToken(String url){
        if (StringUtils.isNullOrEmpty(url)){
            log.warn("url不能为空");
            return null;
        }
        if (!(url.contains("token=")||url.contains("token ="))) {
            log.warn("令牌不能为空");
            return null;
        }
        String[] param = url.split("\\?");
        String[] token = param[1].split("=");
        return token[1];
    }
}
