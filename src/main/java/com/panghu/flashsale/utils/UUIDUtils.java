package com.panghu.flashsale.utils;

import java.util.UUID;

/**
 * @author: 胖虎
 * @date: 2019/6/25 19:05
 **/
public class UUIDUtils {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
