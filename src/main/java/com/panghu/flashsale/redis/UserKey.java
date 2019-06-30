package com.panghu.flashsale.redis;

/**
 * @author: 胖虎
 * @date: 2019/6/20 16:46
 **/
public class UserKey extends BasePrefix{

    private static final int TOKEN_EXPIRE = 1800;

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE, "token");
    public static UserKey id = new UserKey(0, "id");

}
