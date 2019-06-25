package com.panghu.flashsale.validator;

import com.panghu.flashsale.utils.ValidatorUtils;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: 胖虎
 * @date: 2019/6/25 14:44
 **/
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (required){
            return ValidatorUtils.isCellphone(s);
        }
        else{
            if (StringUtils.isEmpty(s)){
                return true;
            }
            return ValidatorUtils.isCellphone(s);
        }
    }
}
