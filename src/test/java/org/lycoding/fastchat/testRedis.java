package org.lycoding.fastchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.lycoding.fastchat.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/13 0:16
 **/
@SpringBootTest
public class testRedis {
    @Autowired
    private RedisCache redisCache;


    @Test
    void currentTime() throws JsonProcessingException {
//        List<String> str = redisCache.getList("U8Hl32CQP7Vi");
//        System.out.println("str = " + str);
//        ContactsInfo contactsInfo = new ContactsInfo();
//        contactsInfo.setContactsId("000000");
//        contactsInfo.setContactsType(1);
//        contactsInfo.setContactStatus(0);
//        System.out.println("contactsInfo = " + contactsInfo);
//
//        for (String s : str) {
//            System.out.println(JsonUtils.jsonToObject(s,ContactsInfo.class));
//        }
    }

    public static void main(String[] args) {
         String userId="U8Hl32CQP7Vi";
        System.out.println(userId.charAt(0));

        char c='q';
        String str ="ffff";
        System.out.println(c+str);
    }

}
