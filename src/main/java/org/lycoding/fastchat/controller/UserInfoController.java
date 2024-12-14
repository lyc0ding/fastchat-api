package org.lycoding.fastchat.controller;

import org.lycoding.fastchat.entity.dto.MessageSendDto;
import org.lycoding.fastchat.entity.vo.Result;
import org.lycoding.fastchat.entity.pojo.UserInfo;
import org.lycoding.fastchat.service.UserInfoService;
import org.lycoding.fastchat.websocket.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/4 21:49
 **/
@RestController
@RequestMapping("/account")
public class UserInfoController extends BaseController{
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageHandler messageHandler;
//    注册
    @PostMapping ("/register")
    public Result registry(@RequestBody UserInfo userInfo){
        //加密密码
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        String userId = userInfoService.registry(userInfo);
        Map<String, String> map = new HashMap<>();
        map.put("userId",userId);
        return getSuccessResponse("注册成功",map);
    }

//    登录
    @PostMapping("/login")
    public Result login(UserInfo userInfo){
        Map<String, Object> token = userInfoService.login(userInfo);
        if (token!=null){
            return getSuccessResponse("登录成功",token);
        }
        return getContentErrorResponse();
    }

//    获取验证码
    @GetMapping("/captcha")
    public Result getCaptcha(){
        Map captcha = userInfoService.getCaptcha();
        return getSuccessResponse("已获取验证码",captcha);
    }

    /**
     * 请求修改无需验证的个人信息
     * sex nickname avator birthday personal_signature area area_code
     */
    @PutMapping("/userInfo")
    public Result updateUserInfo(@RequestBody UserInfo userInfo){
        userInfoService.updateUserInfo(userInfo);
        return getSuccessResponse("信息修改成功",null);
    }

    @RequestMapping("/test")
    public Result test(){
        MessageSendDto<Object> sendDto = new MessageSendDto<>();
        sendDto.setContactsName("Tom");
        sendDto.setMessagesContents("Hello");
        sendDto.setContactsId("UO3rEvUHQW9j");
        messageHandler.sendMsg(sendDto);
        return getSuccessResponse("",null);
    }

}
