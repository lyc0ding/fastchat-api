package org.lycoding.fastchat.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/26 13:04
 **/
public class JsonUtils {
    /**
     * 对象转成json格式  存redis
     */
    public static String objectToJson(Object value){
        String jsonStr = JSONObject.toJSON(value).toString();
        return jsonStr;
    }

    /**
     * json 字符串转对象
     */
    public static <T> T jsonToObject(String json,Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        T t = mapper.readValue(json, clazz);
        return t;
    }
}
