package org.lycoding.fastchat.service.impl;

import com.wf.captcha.ArithmeticCaptcha;
import org.lycoding.fastchat.entity.Constants;
import org.lycoding.fastchat.entity.pojo.UserDetail;
import org.lycoding.fastchat.mapper.ContactsInfoMapper;
import org.lycoding.fastchat.mapper.UserInfoMapper;
import org.lycoding.fastchat.entity.pojo.UserInfo;
import org.lycoding.fastchat.service.ContactsInfoService;
import org.lycoding.fastchat.service.UserInfoService;
import org.lycoding.fastchat.utils.JwtUtils;
import org.lycoding.fastchat.utils.NameUtils;
import org.lycoding.fastchat.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/4 22:12
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ContactsInfoService contactsInfoService;
    @Autowired
    private ContactsInfoMapper contactsInfoMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;
    /**
     * 用户注册
     */
    @Override
    public String registry(UserInfo userInfo) {
        //判断该手机号或者email的用户是否存在
        if (!(Objects.isNull(queryUserByPhoneOrEmail(userInfo)))){
            throw new RuntimeException("该用户已经存在！");
        }
        String userID = Constants.USER_PREFIX+ NameUtils.generateUsername(Constants.GENERATED_NAME_LENGTH);
        //根据用户id判断是否已经存在了该用户,存在了重新生成
        while (!(isExsitUserByUserId(userID))){
            userID = Constants.USER_PREFIX+ NameUtils.generateUsername(Constants.GENERATED_NAME_LENGTH);
        }
        //设置userID
        userInfo.setUserId(userID);
        //注册用户
        userInfoMapper.registry(userInfo);
        contactsInfoService.addContactRobot(userID);
        return userID;
    }

    /**
     * 用户登录
     * 认证主体，将前端传来的认证信息封装
     */
    @Override
    public Map login(UserInfo userInfo) {
        //ctrl+alt+鼠标左键看见Authentication是一个接口，那就去找它的实现类
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo.getUserId(),userInfo.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        /**
         * 将登录后的用户 认证信息，存入redis
         */
        UserDetail userDetail =(UserDetail)authenticate.getPrincipal();
        String userId = userDetail.getUserInfo().getUserId();
        /**
         * 生成用户token ，存入redis 并且返回给 客户端
         */
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userInfo.getUserId());
        String token = JwtUtils.genToken(claims);
        //缓存token 并且设置一个过期时间
        claims.put("token",token);
        redisCache.setValue(Constants.AUTHENTICATION_TOKEN+userId,token,Constants.SIXTY_SECONDS*720);
        redisCache.setValue(Constants.TOKEN_STORE+token,1,Constants.SIXTY_SECONDS*720);
        //登录成功，将该用户的所有联系人信息存进redis
        List<String> contactsInfoList = contactsInfoMapper.queryAllContacts(userId);
        for (String contacts : contactsInfoList) {
            if (contacts.charAt(0)==Constants.USER_PREFIX){
                redisCache.saveContacts(Constants.CONTACTS_FRIEND+userId,contacts);
                continue;
            }
            redisCache.saveContacts(Constants.CONTACTS_GROUP+userId,contacts);
        }
        return claims;
    }

    /**
     * 根据用户ID判断某个用户是否存在
     */
    @Override
    public boolean isExsitUserByUserId(String userId) {
        //userId不存在   返回true
        if (Objects.isNull(userInfoMapper.queryUserByUserId(userId))){
            return true;
        }
        return false;
    }

    /**
     * 根据手机号或者邮箱查询是否存在这个用户
     * 一个手机号、邮箱只能绑定一个账号
     */
    @Override
    public UserInfo queryUserByPhoneOrEmail(UserInfo userInfo) {
        UserInfo queryUser = userInfoMapper.queryUserByPhoneOrEmail(userInfo);
        return queryUser;
    }

    /**
     * 根据user_id修改不重要的用户信息
     */
    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfoMapper.updateUserInfo(userInfo);
    }

    /**
     * 获取验证码
     */
    @Override
    public Map getCaptcha() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha();
        String base64 = captcha.toBase64();
        String text = captcha.text();
        //生成一个验证码id，将验证码 存入redis
        Map<String,String> map = new HashMap<>();
        String captchaId = NameUtils.generateUsername(6);
        map.put("captchaKey",captchaId);
        map.put("captcha",base64);
        redisCache.setValue(Constants.CAPTCHA_ANSWER+captchaId,text,60*10);
        return map;
    }

}

