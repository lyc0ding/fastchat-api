package org.lycoding.fastchat;

import org.lycoding.fastchat.utils.JwtUtils;

import java.util.Map;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/26 14:19
 **/
public class testJwt {
    static String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsidXNlcklkIjoiVWJrS3laYjFybG5UIn0sImV4cCI6MTcyNzM3MTE4OH0.JntDPKD5k8VaaNrnqDfcJRn6u-nFTgqfV1yu0NwSX-M";

    public static void main(String[] args) {
        Map<String, Object> map = JwtUtils.parseToken(token);
        String userId = JwtUtils.getUserId(map);
        System.out.println("userId = " + userId);

    }
}
