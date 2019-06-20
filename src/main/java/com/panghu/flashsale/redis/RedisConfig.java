package com.panghu.flashsale.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 胖虎
 * @date: 2019/6/19 19:58
 **/
@Configuration
@ConfigurationProperties(prefix = "redis")
@Setter
@Getter
public class RedisConfig {
    private String host;
    private int port;
    /**
     * 以秒为单位
     */
    private int timeout;
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    /**
     * 以秒为单位
     */
    private int poolMaxWait;
}
