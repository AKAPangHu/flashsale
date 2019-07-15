package com.panghu.flashsale.redis;

/**
 * @author: 胖虎
 * @date: 2019/7/14 23:05
 **/
public class AccessKey extends BasePrefix{

    public AccessKey(String prefix) {
        super(prefix);
    }

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey getAccessKeyWithExpire(int expire){
        return new AccessKey(expire, "access");
    }
}
