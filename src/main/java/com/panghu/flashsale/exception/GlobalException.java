package com.panghu.flashsale.exception;

import com.panghu.flashsale.result.CodeMsg;
import lombok.Getter;
import lombok.ToString;

/**
 * @author: 胖虎
 * @date: 2019/6/25 15:44
 **/
@Getter
public class GlobalException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }



}
