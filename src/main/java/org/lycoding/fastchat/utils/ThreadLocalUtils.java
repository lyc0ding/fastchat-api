package org.lycoding.fastchat.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/25 21:49
 **/
public class ThreadLocalUtils {
    private final static ThreadLocal<Map<String,Object>> localCache = new ThreadLocal<>();

    /**
     * 添加数据
     */
    public static void setValue(Map map){
        localCache.set(map);
    }

    /**
     * 获取数据
     */
    public static Map getValue(){
        Map<String, Object> map = localCache.get();
        return map;
    }

    public static void remove(){
        localCache.remove();
    }
}
