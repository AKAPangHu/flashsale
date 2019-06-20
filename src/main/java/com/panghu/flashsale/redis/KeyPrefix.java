package com.panghu.flashsale.redis;

/**
 * @author: 胖虎
 * @date: 2019/6/20 16:25
 **/
public interface KeyPrefix {
    public int getExpireSeconds();
    public String getPrefix();
}
