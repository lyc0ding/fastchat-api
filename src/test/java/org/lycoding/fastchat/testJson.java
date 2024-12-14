package org.lycoding.fastchat;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.lycoding.fastchat.entity.pojo.UserInfo;
import org.lycoding.fastchat.utils.NameUtils;

import java.util.Arrays;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/26 12:55
 **/
public class testJson {
    @Test
    void objectToJson() throws JsonProcessingException {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("787878");
        userInfo.setPassword("787878");


        System.out.println("userInfo = " + userInfo);
        Object json = JSONObject.toJSON(userInfo);
        System.out.println("json = " + json);

        String string = json.toString();
        System.out.println("string = " + string);

        ObjectMapper objectMapper = new ObjectMapper();

        UserInfo userInfo1 = objectMapper.readValue(string, UserInfo.class);
        System.out.println("userInfo1 = " + userInfo1);


    }

    public static void main(String[] args) {
        String str = new String("grg");


    }
}
