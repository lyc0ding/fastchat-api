package org.lycoding.fastchat.service;

import org.lycoding.fastchat.entity.pojo.ContactsInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/13 21:11
 **/
public interface ContactsInfoService {
    /**
     * 根据用户id 查询所有正常状态联系人
     */
    public List<ContactsInfo> queryContactsByStatus(String userId);

    /**
     * 根据id查找某个联系人
     */
    ContactsInfo queryContactsById(String userId, String queryId);

    /**
     * 添加联系人
     */
    void addContacts(ContactsInfo contactsInfo);

    /**
     * 注册成功后，添加机器人为好友
     * 创建会话信息
     * 机器人给用户发送一条打招呼信息
     */
    public void addContactRobot(String userId);
}
