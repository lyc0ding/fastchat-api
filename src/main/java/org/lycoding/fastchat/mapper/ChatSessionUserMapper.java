package org.lycoding.fastchat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.lycoding.fastchat.entity.pojo.ChatSession;
import org.lycoding.fastchat.entity.pojo.ChatSessionUser;

import java.util.List;

/**
 * 用户与联系人的会话信息
 **/
@Mapper
public interface ChatSessionUserMapper {
    /**
     * 查询所有的会话信息
     */
    public List<ChatSessionUser> chatSessionUserList(String userId);

    /**
     * 添加会话信息
     */
    @Insert("insert into chat_session_user(user_id,contacts_id,session_id,contacts_remarks) values(#{UserId},#{ContactsId},#{SessionId},#{ContactsRemarks})")
    public void addChatSessionUser(ChatSessionUser chatSessionUser);
}
