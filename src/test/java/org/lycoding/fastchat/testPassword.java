package org.lycoding.fastchat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/8 21:37
 **/
@SpringBootTest
public class testPassword {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void encoder() {
//        加密
        System.out.println(encoder.encode("123456"));
//        解码
        System.out.println(encoder.matches("123456","$2a$10$8aNX0elTxnDlq8JmGU2mtuAFBPnN3wlQ3SdOtE3yyKj3KPbuFC/hO"));
    }
}
