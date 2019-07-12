package com.panghu.flashsale.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: 胖虎
 * @date: 2019/6/17 8:29
 **/
@Getter
@Setter
@ToString
public class CodeMsg {

    private int code;
    private String msg;

    //通用的错误码

    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "非法请求");

    //登录模块 5002XX

    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效，请尝试重新登录");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg CELLPHONE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg CELLPHONE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static CodeMsg CELLPHONE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");

    //商品模块 5003XX

    //订单模块 5004XX

    public static CodeMsg ORDER_NOT_EXIST= new CodeMsg(500400, "订单不存在");

    //秒杀模块 5005XX

    public static CodeMsg FLASH_SALE_GOODS_STOCK_EMPTY = new CodeMsg(500500, "秒杀商品售罄");
    public static CodeMsg REPEAT_RUSH= new CodeMsg(500501, "请勿重复秒杀");
    public static CodeMsg FLASH_SALE_IS_OVER= new CodeMsg(500502, "秒杀已经结束");



    private CodeMsg( ) {
    }

    private CodeMsg( int code,String msg ) {
        this.code = code;
        this.msg = msg;
    }


    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String filledMessage = String.format(this.msg, args);
        return new CodeMsg(code, filledMessage);
    }



}