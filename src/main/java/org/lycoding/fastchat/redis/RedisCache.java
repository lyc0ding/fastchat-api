package org.lycoding.fastchat.redis;

import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.lycoding.fastchat.entity.Constants;
import org.lycoding.fastchat.entity.pojo.ContactsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/4 21:50
 **/
@Component
@Slf4j
public class RedisCache<V> {
    @Autowired
    private RedisTemplate<String,V> redisTemplate;

    /**
     * 缓存客户端 验证码、token
     */
    public void setValue(String key, V value,long time){
        try{
            if (time>0){
                redisTemplate.opsForValue().set(key,value, time, TimeUnit.SECONDS);
            }else {
                redisTemplate.opsForValue().set(key,value);
            }
        }
        catch (Exception e){
            log.warn("Redis key:{} value:{}设置失败",key,value);
            e.printStackTrace();
        }
    }

    /**
     * 获取token、验证码答案
     */
    public String getValue(String key){
        V value = null;
        if(!(StringUtils.isNullOrEmpty(key))){
            value = redisTemplate.opsForValue().get(key);
        }

        if (value==null){
            return "";
        }
        String stringValue = value.toString();
        return stringValue;
    }

    /**
     * 用户登录成功时 存入联系人信息
     */
    public void saveContacts(String key,V value){
        redisTemplate.opsForList().leftPush(key,value);
        redisTemplate.expire(key,60000,TimeUnit.SECONDS);
        return;
    }

    /**
     * 获取联系人信息
     */
    // 获取 List 中的所有元素
    public List<String> getContacts(String key) {
        return (List<String>)redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存用户心跳
     */
    public void saveHeartBeat(String userId,V value){
        redisTemplate.opsForValue().set(Constants.USER_HEART_BEAT+userId,value,Constants.SIXTY_SECONDS,TimeUnit.SECONDS);
    }
    /**
     * 获取用户心跳
     */
    public V getHeartBeat(String userId){
        V v = redisTemplate.opsForValue().get(Constants.USER_HEART_BEAT + userId);
        return v;
    }
}
