package org.lycoding.fastchat.websocket;

import com.mysql.cj.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.lycoding.fastchat.entity.dto.MessageSendDto;
import org.lycoding.fastchat.utils.JsonUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: lycoding
 * @Description: 消息处理器：做消息中转(消息分发)
 * @DateTime: 2024/10/11 21:55
 **/
@Component("MessageHandler")
@Slf4j
public class MessageHandler {
    private static final String MESSAGE_TOPIC="message.topic";

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ChannelContextUtils contextUtils;

    @PostConstruct
    public void listenMsg(){
        RTopic rTopic=redissonClient.getTopic(MESSAGE_TOPIC);
        rTopic.addListener(MessageSendDto.class,(MessageSendDto,sendDto)->{
            //接收人id
            String receiveId=sendDto.getContactsId();
            if (!StringUtils.isNullOrEmpty(receiveId)) {
                contextUtils.sendMessage(sendDto,receiveId);
            }
        });
    }

    public void sendMsg(MessageSendDto messageSendDto){
        RTopic rTopic=redissonClient.getTopic(MESSAGE_TOPIC);
        rTopic.publish(messageSendDto);
    }
}
