package org.lycoding.fastchat.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/6 18:16
 **/
public class NameUtils {
//    指定随机用户名生成范围
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

//    生成指定长度的用户Id
    public static String generateUsername(int length) {
        StringBuilder username = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            username.append(CHARACTERS.charAt(index));
        }
        return username.toString();
    }

//    生成sessionId
    public static String getSessionId(String[] userId){
        Arrays.sort(userId);
        String str = userId[0] + userId[1];
        return DigestUtils.md5Hex(str);
    }
}
