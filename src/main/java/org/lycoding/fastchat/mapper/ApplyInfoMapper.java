package org.lycoding.fastchat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.lycoding.fastchat.entity.pojo.ApplyInfo;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/11 10:42
 **/
@Mapper
public interface ApplyInfoMapper {
    /**
     * 根据用户id、联系人id 添加好友、群组
     */
    @Insert("insert into user_apply(apply_user_id,join_id,join_type) values(#{applyUserId},#{joinId},#{joinType}))")
    public void createApply(ApplyInfo applyInfo);

    /**
     * 查找未读的申请消息
     */
    public int queryApplyCount(ApplyInfo applyInfo);

    /**
     * 处理申请
     */
    public void handleApply(ApplyInfo applyInfo);
}
