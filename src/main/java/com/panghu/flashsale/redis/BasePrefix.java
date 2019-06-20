package com.panghu.flashsale.redis;

/**
 * @author: 胖虎
 * @date: 2019/6/20 16:26
 **/
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int getExpireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String classname = getClass().getSimpleName();
        return classname + ":"+ prefix;
    }
}
