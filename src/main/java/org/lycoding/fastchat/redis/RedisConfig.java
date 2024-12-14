package org.lycoding.fastchat.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Author: lycoding
 * @Description: TODO
 * @DateTime: 2024/9/5 17:37
 **/
@Configuration
public class RedisConfig<V> {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Bean
    public RedisTemplate<String,V> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
//        设置Key的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
//        设置value的序列化方式
        redisTemplate.setValueSerializer(RedisSerializer.json());
//        设置hash  key的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
//        设置hash  value的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.json());

        return redisTemplate;
    }

//    注入redisson配置Bean
    @Bean(name="redissonClient",destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        try{
            Config config = new Config();
            config.useSingleServer().setAddress("redis://192.168.10.101:6379");
            RedissonClient redissonClient= Redisson.create(config);
            return redissonClient;
        }catch (Exception e){
            RuntimeException runtimeException = new RuntimeException("redisson连接错误");
        }
        return null;
    }
}
