package org.lycoding.fastchat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lycoding.fastchat.entity.pojo.UserInfo;

import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/4 21:50
 **/

@Mapper
public interface UserInfoMapper {
    /**
     * 注册
     * 该对象中只包含用户名和密码
     */
    @Insert("insert into user_info(user_id,password,phone,email,nickname,create_time,update_time) value(#{userId},#{password},#{phone},#{email},#{nickname},now(),now())")
    public void registry(UserInfo userInfo);

    /**
     * 根据用户ID查询这个用户是否存在
     */
    @Select("SELECT user_id ,password FROM user_info WHERE user_id = #{userId} LIMIT 1")
    public UserInfo queryUserByUserId(String userId);
    /**
     * 根据手机号或者邮箱查询是否存在这个用户
     */
    public UserInfo queryUserByPhoneOrEmail(UserInfo userInfo);

    /**
     * 用户登录
     */
    String login(UserInfo userInfo);

    /**
     *根据user_id修改不重要的用户信息
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 记录最后一次登录时间
     */
    @Update("update user_info set last_login_time = now() where user_id = #{userId}")
    void setLastLoginTime(String userId);

    /**
     * 记录最后一次离线时间
     */
    @Update("update user_info set last_off_time = now() where user_id = #{userId}")
    void setLastOffTime(String userId);

    /**
     * 查询最后一次离线时间
     */
    @Select("select last_off_time from user_info where user_id = #{userId}")
    Date queryLastOffTimeByUserId(String userId);
}
