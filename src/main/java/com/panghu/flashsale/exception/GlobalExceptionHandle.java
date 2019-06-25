package com.panghu.flashsale.exception;

import com.panghu.flashsale.result.CodeMsg;
import com.panghu.flashsale.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: 胖虎
 * @date: 2019/6/25 15:29
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandle{

    @ExceptionHandler({Exception.class})
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){

        //检测是否为BindException
        if (e instanceof BindException){
            BindException bindException = (BindException) e;
            //获取全部错误列表
            List<ObjectError> allErrors = bindException.getAllErrors();
            ObjectError error = allErrors.get(0);
            String msg = error.getDefaultMessage();
            //将错误信息添加进绑定错误码的字符串中，返回错误信息
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }

        else if (e instanceof GlobalException){
            GlobalException globalException = (GlobalException) e;
            return Result.error(globalException.getCodeMsg());
        }


        return Result.error(CodeMsg.SERVER_ERROR);
    }

}
