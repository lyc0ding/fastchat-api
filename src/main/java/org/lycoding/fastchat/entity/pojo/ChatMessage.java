package org.lycoding.fastchat.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/28 22:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String sessionId;  //用户 session id
    private int messageType;  //发送的消息类型
    private String messageContent;  //消息内容: 文字、图片、文件
    private String sendUserId;   //发送人id
    private String sendUserNickname;  //发送人的名称
    private Date sendTime;  //发送时间
    private String contactsId;  //联系人id
    private int contactsType;  // 联系人类型：0：好友  1：群聊
    private int fileSize;  //文件大小
    private String fileName;  //文件名
    private int fileType;  //文件类型
    private int status;  //发送状态：0：正在发送  1：已发送
    private Integer messageCount;
}
