package org.lycoding.fastchat.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysql.cj.util.StringUtils;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: 消息发送的格式（消息载体）
 * @DateTime: 2024/9/29 0:17
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MessageSendDto<T> implements Serializable {
    private static final long serialVersionUID=-1045517597894L;
    //消息ID
    private Long messageId;
    //会话ID
    private String sessionId;
    //发送人Id
    private String sendUserId;
    //发送人昵称
    private String sendUserNickName;
    //联系人Id
    private String contactsId;
    //联系人类型
    private Integer contactsType;
    //联系人名称
    private String contactsName;
    //消息内容
    private String messagesContents;
    //最后消息
    private String lastMessages;
    //消息类型
    private Integer messageType;
    //发送时间
    private Date sendTime;
    //扩展信息
    private T extendData;

    //消息状态
    private Integer status;
    //群员数
    private Integer memberCount;

    //文件信息
    private Long fileSize;
    private String fileName;
    private Integer fileType;
    public String getLastMessage(){
        if (StringUtils.isNullOrEmpty(lastMessages)){
            return messagesContents;
        }
        return lastMessages;
    }
}

