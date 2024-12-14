package org.lycoding.fastchat.service.impl;

import com.mysql.cj.util.StringUtils;
import org.lycoding.fastchat.entity.Constants;
import org.lycoding.fastchat.mapper.GroupInfoMapper;
import org.lycoding.fastchat.entity.pojo.GroupInfo;
import org.lycoding.fastchat.service.GroupInfoService;
import org.lycoding.fastchat.utils.NameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/7 18:13
 **/
@Service
public class GroupInfoServiceImpl implements GroupInfoService {
    @Autowired
    private GroupInfoMapper groupInfoMapper;
    /**
     * 创建群聊
     */
    @Override
    public void createGroups(GroupInfo groupInfo) {
        //生成 group_id 并设置到该对象中
        String groupId=Constants.GROUP_PREFIX + NameUtils.generateUsername(Constants.GENERATED_NAME_LENGTH);
        groupInfo.setGroupId(groupId);
        //创建群聊的用户默认是群主
        groupInfo.setGroupHeadman(groupInfo.getCreateUser());
        //判断用户创建群聊时是否设置群名，否则给一个默认名称
        if (StringUtils.isNullOrEmpty(groupInfo.getGroupName())){
            groupInfo.setGroupName("群聊 "+groupId);
        }
        groupInfoMapper.createGroups(groupInfo);
    }

    /**
     * 加入群聊
     */
    @Override
    public void addGroupById(GroupInfo groupInfo) {
    }

}
