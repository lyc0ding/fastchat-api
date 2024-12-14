package org.lycoding.fastchat;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.lycoding.fastchat.websocket.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/4 21:47
 **/
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@MapperScan("org/lycoding/fastchat/mapper")
@EnableAspectJAutoProxy
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
        log.info("项目启动成功");

    }
}
