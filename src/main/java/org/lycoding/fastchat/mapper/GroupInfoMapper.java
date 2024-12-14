package org.lycoding.fastchat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.lycoding.fastchat.entity.pojo.GroupInfo;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/7 18:13
 **/
@Mapper
public interface GroupInfoMapper {
    /**
     * 创建群聊
     */
    @Insert("insert into group_info(" +
            "group_id,group_name,group_owner_id,create_user,create_time,status) " +
            "values(#{groupId},#{groupName},#{groupOwnerId},#{createUser},now(),1)")
    public void createGroups(GroupInfo groupInfo);

}
