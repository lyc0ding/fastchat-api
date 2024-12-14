package org.lycoding.fastchat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.lycoding.fastchat.entity.pojo.ChatMessage;

import java.sql.Date;
import java.util.List;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/10/10 12:14
 **/
@Mapper
public interface ChatMessageMapper {
    /**
     * 添加会话会话的消息
     */
    @Insert("insert into chat_message(session_id,message_type,message_content,send_user_id,send_user_nickname,contacts_id,contacts_type,status,send_time) values(#{sessionId},#{messageType},#{messageContent},#{sendUserId},#{sendUserNickname},#{contactsId},#{contactsType},#{status},now())")
    public void addChatMessage(ChatMessage chatMessage);

    /**
     * 查找所有消息
     */
    @Select("select * from chat_message where contacts_id=#{contactsId} and #{lastReceiveTime} >= send_time")
    public List<ChatMessage> queryAllMessage(String contactsId, Date lastReceiveTime);
}
