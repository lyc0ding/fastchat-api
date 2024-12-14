package org.lycoding.fastchat.websocket;

import com.mysql.cj.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.lycoding.fastchat.entity.Constants;
import org.lycoding.fastchat.entity.dto.MessageSendDto;
import org.lycoding.fastchat.entity.dto.WsInitData;
import org.lycoding.fastchat.entity.enums.MessageTypeEnum;
import org.lycoding.fastchat.entity.pojo.*;
import org.lycoding.fastchat.mapper.*;
import org.lycoding.fastchat.utils.JsonUtils;
import org.lycoding.fastchat.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: lycoding
 * @Description: 自定义的WS通道工具类
 * @DateTime: 2024/9/12 19:14
 **/
@Component
public class ChannelContextUtils {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ChatSessionUserMapper chatSessionUserMapper;
    @Autowired
    private ApplyInfoMapper applyInfoMapper;
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private WsInitData wsInitData;

    @Autowired
    private RedisCache redisCache;
    private static final ConcurrentHashMap<String,Channel> USER_CONTEXT_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ChannelGroup> GROUP_CONTEXT_MAP = new ConcurrentHashMap<>();
    /**
     * 给每条通道绑定上userId
     * channel().id() 可以获取这条通道的唯一标识
     */
    public void addChannelContext(String userId, Channel channel){
        String channelId=channel.id().toString();
        AttributeKey attributeKey = null;
        if(!AttributeKey.exists(channelId)){
            attributeKey=AttributeKey.newInstance(channelId);
        }else {
            attributeKey=AttributeKey.valueOf(channelId);
        }
        channel.attr(attributeKey).set(userId);

        // hashmap通过userid 、 channel管理channel
        USER_CONTEXT_MAP.put(userId,channel);

        //记录最后一次登录时间
        userInfoMapper.setLastLoginTime(userId);

        /**
         * 每次登录成功后，给用户发送的消息：会话信息、三天内的聊天信息、用户申请信息
         * 只查询用户三天内的消息
         */
        long lastLoginTime = userInfoMapper.queryLastOffTimeByUserId(userId).getTime();
        if (System.currentTimeMillis()- Constants.MILLIS_3DAY_AGO>lastLoginTime){
            lastLoginTime=System.currentTimeMillis()-Constants.MILLIS_3DAY_AGO;
        }
        //查询所有会话信息，保证设备迁移会话信息还在
        List<ChatSessionUser> chatSessions = chatSessionUserMapper.chatSessionUserList(userId);
        //封装到dto
        wsInitData.setChatSessionUsers(chatSessions);
        //查询聊天消息

        Date lastReceiveTime = userInfoMapper.queryLastOffTimeByUserId(userId);  //最后离线时间--最后接收消息时间
        List<String> groupIdList=redisCache.getContacts(Constants.CONTACTS_GROUP+userId);  //所有群组
        groupIdList.add(userId);
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (String contactsId : groupIdList) {
            List<ChatMessage> messages = chatMessageMapper.queryAllMessage(contactsId,lastReceiveTime);
            chatMessages.addAll(messages);
        }
        wsInitData.setChatMessages(chatMessages);

        //查询好友申请条数(群组申请条数)
        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setJoinId(userId);
        applyInfo.setLastReceiveTime(lastReceiveTime);
        int applyCount = applyInfoMapper.queryApplyCount(applyInfo);
        wsInitData.setApplyCount(applyCount);

        //发送消息:ws连接成功发送消息
        MessageSendDto messageSendDto=new MessageSendDto();
        messageSendDto.setMessageType(MessageTypeEnum.INIT.getType());
        messageSendDto.setContactsId(userId);
        messageSendDto.setExtendData(wsInitData);  // 初始化信息就是额外消息
        sendMessage(messageSendDto,userId);
    }

    /**
     * 发消息
     */
    public void sendMessage(MessageSendDto messageSendDto,String receiveUserId){
        //查看该用户是否在线，在线则存在channel
        if (receiveUserId==null){
            return;
        }
        Channel sendChannel = USER_CONTEXT_MAP.get(receiveUserId);
        if (sendChannel==null){
            return;
        }
        messageSendDto.setContactsId(messageSendDto.getSendUserId());
        messageSendDto.setContactsName(messageSendDto.getSendUserNickName());
        sendChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(messageSendDto)));
    }

    /**
     * 加群
     */
    private void addGroup(String groupId,Channel channel){
        ChannelGroup channelGroup = GROUP_CONTEXT_MAP.get(groupId);
        if (channelGroup == null){
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            GROUP_CONTEXT_MAP.put(groupId,channelGroup);
        }
        channelGroup.add(channel);
    }

    /**
     * 发消息到群组
     */
    public void sendMsgToGroup(String message){
        ChannelGroup channelGroup = GROUP_CONTEXT_MAP.get("000000");
        channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }

    /**
     * 断开连接
     */
    public void removeContext(Channel channel){
        Attribute<String> attr = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attr.get();
        if (StringUtils.isNullOrEmpty(userId)){
            USER_CONTEXT_MAP.remove(userId);
        }
        //记录用户最后掉线时间
        userInfoMapper.setLastOffTime(userId);
//        停止心跳
    }

}
