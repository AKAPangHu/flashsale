package com.panghu.flashsale.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 用户类
 * @author: 胖虎
 * @date: 2019/6/17 8:31
 **/
@Getter
@Setter
@ToString
public class User {
    private long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private int loginCount;
}

