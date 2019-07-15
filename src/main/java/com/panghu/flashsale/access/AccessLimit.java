package com.panghu.flashsale.access;

import com.panghu.flashsale.validator.IsMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author: 胖虎
 * @date: 2019/7/14 18:29
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    int seconds() default 5;

    int maxCount() default 5;

    boolean needLogin() default true;

}
