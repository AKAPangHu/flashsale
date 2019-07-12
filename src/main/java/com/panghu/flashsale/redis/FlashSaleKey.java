package com.panghu.flashsale.redis;

/**
 * @author: 胖虎
 * @date: 2019/7/6 19:34
 **/
public class FlashSaleKey extends BasePrefix {

    public FlashSaleKey(String prefix) {
        super(prefix);
    }

    public FlashSaleKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static FlashSaleKey isOver = new FlashSaleKey("io");

    public static FlashSaleKey flashSalePath = new FlashSaleKey(60,"flsp");
}
