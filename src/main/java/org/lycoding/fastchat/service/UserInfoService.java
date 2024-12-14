package org.lycoding.fastchat.service;

import org.lycoding.fastchat.entity.pojo.UserInfo;

import java.util.Map;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/4 21:49
 **/
public interface UserInfoService {
//    用户注册
    public String registry(UserInfo userInfo);

    /**
     * 用户登录
     */
    public Map<String,Object> login(UserInfo userInfo);

    /**
     * 根据用户ID判断某个用户是否存在
     */
    public boolean isExsitUserByUserId(String userId);

    /**
     *根据手机号或者邮箱查询是否存在这个用户
     */
    public UserInfo queryUserByPhoneOrEmail(UserInfo userInfo);
    /**
     *根据user_id修改不重要的用户信息
     */
    public void updateUserInfo(UserInfo userInfo);

    /**
     * 获取验证码
     */
    public Map getCaptcha();
}
