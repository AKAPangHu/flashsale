package com.panghu.flashsale.utils;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 胖虎
 * @date: 2019/6/23 15:52
 **/
public class ValidatorUtils {

    private static final Pattern CELLPHONE_PATTEN = Pattern.compile("1\\d{10}");

    public static boolean isCellphone(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher matcher = CELLPHONE_PATTEN.matcher(src);
        return matcher.matches();
    }
}

