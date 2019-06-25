package com.panghu.flashsale.vo;

import com.panghu.flashsale.validator.IsMobile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author: 胖虎
 * @date: 2019/6/22 21:23
 **/
@Setter
@Getter
@ToString
public class LoginVo {
    @NotNull
    @IsMobile
    private String cellphone;
    @NotNull
    private String password;
}
