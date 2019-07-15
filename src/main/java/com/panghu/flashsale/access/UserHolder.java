package com.panghu.flashsale.access;

import com.panghu.flashsale.domain.User;

/**
 * @author: 胖虎
 * @date: 2019/7/14 21:51
 **/
public class UserHolder {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void set(User user){
        userThreadLocal.set(user);
    }

    public static User get(){
        return userThreadLocal.get();
    }

    public static void remove(){
        userThreadLocal.remove();
    }

}
