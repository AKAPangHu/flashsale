package com.panghu.flashsale.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author: 胖虎
 * @date: 2019/6/22 21:04
 **/
public class MD5Utils {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String SALT = "1a2b3c4d";

    private static String encryptPassword(String password, String salt){
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + SALT.charAt(7) + password + SALT.charAt(1) + SALT.charAt(2) + SALT.charAt(3);
        return md5(str);
    }

    public static String handlePassword(String password, String salt){
        return encryptPassword(password, salt);
    }


}
