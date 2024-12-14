package org.lycoding.fastchat.service;

import org.lycoding.fastchat.entity.pojo.GroupInfo;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/7 18:12
 **/
public interface GroupInfoService {
    /**
     * 创建群聊
     */
    void createGroups(GroupInfo groupInfo);

    /**
     * 加入群聊
     */
    void addGroupById(GroupInfo groupInfo);
}
