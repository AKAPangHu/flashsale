package com.panghu.flashsale.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * @author: 胖虎
 * @date: 2019/6/19 20:32
 **/
@Component
public class JedisPoolFactory {

    private final RedisConfig redisConfig;

    public JedisPoolFactory(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Bean("jedisPool")
    public JedisPool jedisPoolFactory(){
        redis.clients.jedis.JedisPoolConfig poolConfig = new redis.clients.jedis.JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(),
                redisConfig.getPort(), redisConfig.getTimeout() * 1000);
        return jp;
    }
}
