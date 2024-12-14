package org.lycoding.fastchat.service.impl;

import org.lycoding.fastchat.controller.BaseController;
import org.lycoding.fastchat.mapper.UserInfoMapper;
import org.lycoding.fastchat.entity.pojo.UserDetail;
import org.lycoding.fastchat.entity.pojo.UserInfo;
import org.lycoding.fastchat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: lycoding
 * @DateTime: 2024/9/8 22:05
 **/
@Service
public class LoginSeviceImpl extends BaseController implements UserDetailsService{
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *Authenticate传来的用户名去数据库中查是否存在该用户名
     * 存在则将该用户名对应的行数据封装成一个对象返回
     */
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserInfo user = userInfoMapper.queryUserByUserId(userId);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在！");
        }
        UserDetail userDetail = new UserDetail(user);
        return userDetail;

    }
}
