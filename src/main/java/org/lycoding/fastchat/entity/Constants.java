package org.lycoding.fastchat.entity;

import java.util.concurrent.TimeUnit;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/6 18:28
 **/
public class Constants {
    /**
     * 随机用户id、群id生成器生成的id固定长度
     */
    public static final int GENERATED_NAME_LENGTH=11;

    /**
     * 用户id跟群id都是固定12位
     * 用户id为  U+12位随机生成字母数字组合
     * 群id为  G+12位随机生成字母数字组合
     */
    public static final char USER_PREFIX='U';
    public static final char GROUP_PREFIX='G';
    public static Integer FRIEND_TYPE=0;
    public static Integer GROUP_TYPE=1;

    /**
     * jwt加解密 密钥
     */
    public static final String JWT_KEY="lycoding";

    /**
     * 时间常量设置
     */
    public static final Integer SIXTY_SECONDS=60;
    //三天前
    public static final Long MILLIS_3DAY_AGO=3*24*60*60*1000L;

    /**
     * redis 缓存 key
     */
    public static final String AUTHENTICATION_TOKEN="login:token:";
    public static final String TOKEN_STORE="token:store:";
    public static final String CAPTCHA_ANSWER="request:captcha:";
    public static final String LOGIN_USER_DETAIL="login:user:";

    //用户联系人 好友、群组标签

    public static final String CONTACTS_FRIEND="contacts_lists:friend:";
    public static final String CONTACTS_GROUP="contacts_lists:group:";

    //用户心跳
    public static final String USER_HEART_BEAT="user:heartbeat:";

    //机器人基本信息
    public static final String ROBOT_ID="000000";
    public static final String ROBOT_NAME="文件助手";
    public static final String ROBOT_MESSAGE="欢迎使用FastChat";



}
