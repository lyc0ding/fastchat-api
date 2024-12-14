package org.lycoding.fastchat.entity.dto;

import org.lycoding.fastchat.entity.pojo.ChatMessage;
import org.lycoding.fastchat.entity.pojo.ChatSessionUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * @Author: lycoding
 * @Description: 用户登录成功后，ws初始化完成给用户发送离线消息
 * @DateTime: 2024/9/28 21:57
 **/
@Repository
public class WsInitData {
    //会话用户
    private List<ChatSessionUser> chatSessionList;
    //离线消息
    private List<ChatMessage> chatMessages;
    //申请条数
    private Integer applyCount;
    //最后接收消息
    private String lastReceiveMessage;
    //最后接收消息时间
    private Date lastReceiveTime;

    public List<ChatSessionUser> getChatSessionUsers() {
        return chatSessionList;
    }

    public void setChatSessionUsers(List<ChatSessionUser> chatSessionUsers) {
        this.chatSessionList = chatSessionUsers;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public Integer getAccountApply() {
        return applyCount;
    }

    public void setApplyCount(Integer accountApply) {
        this.applyCount = accountApply;
    }
}
