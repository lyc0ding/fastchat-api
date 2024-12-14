package org.lycoding.fastchat.service.impl;

import org.lycoding.fastchat.entity.Constants;
import org.lycoding.fastchat.entity.enums.MessageStatusEnum;
import org.lycoding.fastchat.entity.enums.MessageTypeEnum;
import org.lycoding.fastchat.entity.enums.UserContactStatusEnum;
import org.lycoding.fastchat.entity.pojo.ChatMessage;
import org.lycoding.fastchat.entity.pojo.ChatSession;
import org.lycoding.fastchat.entity.pojo.ChatSessionUser;
import org.lycoding.fastchat.mapper.ChatMessageMapper;
import org.lycoding.fastchat.mapper.ChatSessionMapper;
import org.lycoding.fastchat.mapper.ChatSessionUserMapper;
import org.lycoding.fastchat.mapper.ContactsInfoMapper;
import org.lycoding.fastchat.entity.pojo.ContactsInfo;
import org.lycoding.fastchat.service.ContactsInfoService;
import org.lycoding.fastchat.utils.NameUtils;
import org.lycoding.fastchat.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/13 21:13
 **/
@Service
public class ContactsInfoServiceImpl implements ContactsInfoService {
    @Autowired
    private ContactsInfoMapper contactsInfoMapper;
    @Autowired
    private ChatSessionUserMapper chatSessionUserMapper;
    @Autowired
    private ChatSessionMapper chatSessionMapper;
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 根据用户id 查询所有正常状态联系人
     */
    @Override
    public List<ContactsInfo> queryContactsByStatus(String userId) {
        List<ContactsInfo> contactsInfoList = contactsInfoMapper.queryContactsByStatus(userId);
        return contactsInfoList;
    }

    /**
     * 根据id查找某个联系人
     */
    @Override
    public ContactsInfo queryContactsById(String userId, String queryId) {
        return contactsInfoMapper.queryContactsById();
    }

    /**
     * 添加联系人
     */
    @Override
    public void addContacts(ContactsInfo contactsInfo) {
        contactsInfoMapper.addContactsById(contactsInfo);
    }

    /**
     * 注册成功后，添加机器人为好友
     * 创建会话信息
     * 机器人给用户发送一条打招呼信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addContactRobot(String userId) {
        //添加机器人为好友
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setUserId(userId);
        contactsInfo.setContactsId(Constants.ROBOT_ID);
        contactsInfo.setContactsRemarks(Constants.ROBOT_NAME);
        contactsInfo.setContactsType(Constants.FRIEND_TYPE);
        contactsInfoMapper.addContacts(contactsInfo);
        //创建用户会话信息
        ChatSessionUser chatSessionUser = new ChatSessionUser();
        String sessionId= NameUtils.getSessionId(new String[]{userId,Constants.ROBOT_ID});//生成会话id
        chatSessionUser.setUserId(userId);
        chatSessionUser.setContactsId(Constants.ROBOT_ID);
        chatSessionUser.setSessionId(sessionId);
        chatSessionUser.setContactsRemarks(Constants.ROBOT_NAME);
        chatSessionUserMapper.addChatSessionUser(chatSessionUser);
        //创建聊天会话
        ChatSession chatSession = new ChatSession();
        chatSession.setSessionId(sessionId);
        chatSession.setLastReceiveMessage(Constants.ROBOT_MESSAGE);
        chatSessionMapper.addChatSession(chatSession);
        //添加聊天消息
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSendUserId(Constants.ROBOT_ID);
        chatMessage.setSendUserNickname(Constants.ROBOT_NAME);
        chatMessage.setMessageType(MessageTypeEnum.CHAT.getType());
        chatMessage.setMessageContent(Constants.ROBOT_MESSAGE);
        chatMessage.setSessionId(sessionId);
        chatMessage.setContactsId(userId);
        chatMessage.setContactsType(UserContactStatusEnum.FRIEND.getStatus());
        chatMessage.setStatus(MessageStatusEnum.SENT.getCode());
        chatMessageMapper.addChatMessage(chatMessage);
    }


}
