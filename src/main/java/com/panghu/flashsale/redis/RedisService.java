package com.panghu.flashsale.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author: 胖虎
 * @date: 2019/6/19 20:06
 **/
@Service
public class RedisService {

    private final JedisPool jedisPool;

    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取对象
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String strInRedis = jedis.get(realKey);
            return stringToBean(strInRedis, clazz);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断是否存在对象
     */
    public boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 自增（原子操作）
     */
    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 自减（原子操作）
     */
    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            String waitingToStore = beanToString(value);
            //将{类名：前缀名}和要查询的key 拼接成真正的key
            String realKey = prefix.getPrefix() + key;

            int expireSeconds = prefix.getExpireSeconds();
            //如果seconds小于0，默认无期限
            if (expireSeconds <= 0) {
                jedis.set(realKey, waitingToStore);
            } else {
                jedis.setex(realKey, expireSeconds, waitingToStore);
            }

            if (waitingToStore == null || waitingToStore.length() <= 0) {
                return false;
            }
            return true;

        } finally {
            returnToPool(jedis);
        }
    }
    public boolean del(KeyPrefix prefix, String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            //将{类名：前缀名}和要查询的key 拼接成真正的key
            String realKey = prefix.getPrefix() + key;
            Long success = jedis.del(realKey);
            return success > 0;
        }
    }


    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
