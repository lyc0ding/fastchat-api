package org.lycoding.fastchat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/10/12 10:52
 **/
@SpringBootTest
public class testProperties {
    @Value("${spring.data.redis.host}")
    String host;

    @Test
    void properties() {
        System.out.println("host = " + host);

        String wsPort=System.getProperty("server.port");
        System.out.println("wsPort = " + wsPort);
    }
}
