package org.lycoding.fastchat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.lycoding.fastchat.entity.pojo.ChatSession;

import java.util.List;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/28 21:37
 **/
@Mapper
public interface ChatSessionMapper {
    /**
     * 添加会话信息
     */
    @Insert("insert into chat_session(session_id,last_receive_message,last_receive_time) values(#{sessionId},#{lastReceiveMessage},now())")
    public void addChatSession(ChatSession chatSession);

}
