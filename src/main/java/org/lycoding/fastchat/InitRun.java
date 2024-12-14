package org.lycoding.fastchat;

import lombok.extern.slf4j.Slf4j;
import org.lycoding.fastchat.redis.RedisCache;
import org.lycoding.fastchat.websocket.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * ApplicationRunner方法用处：
 * 执行启动时逻辑：可以在应用启动后立即执行一些逻辑，而不需要在主程序中进行操作。
 * 初始化数据：可以用来初始化数据库或加载一些配置信息。
 * 异步启动任务：可以在应用启动的同时进行一些后台任务的执行
 **/
@Component("initRun")
@Slf4j
public class InitRun implements ApplicationRunner {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private NettyServer netty;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            netty.nettyStart();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
