package org.lycoding.fastchat;

import org.junit.jupiter.api.Test;
import org.lycoding.fastchat.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/28 21:21
 **/
@SpringBootTest
public class testSql {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void lastLoginTime() throws InterruptedException {
        Date date = userInfoMapper.queryLastOffTimeByUserId("UbkKyZb1rlnT");
        System.out.println("date = " + date);
        long time = date.getTime();
        System.out.println("time = " + time);


        System.out.println(new java.util.Date().getTime());
        System.out.println(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        System.out.println(new Date(System.currentTimeMillis()));
    }
}
