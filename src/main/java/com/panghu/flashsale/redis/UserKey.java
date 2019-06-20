package com.panghu.flashsale.redis;

/**
 * @author: 胖虎
 * @date: 2019/6/20 16:46
 **/
public class UserKey extends BasePrefix{

    public UserKey(String prefix){
        super(prefix);
    }

    public static UserKey getById(){
        return new UserKey("id");
    }
    public static UserKey getByName(){
        return new UserKey("name");
    }

}
