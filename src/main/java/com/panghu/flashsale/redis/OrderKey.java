package com.panghu.flashsale.redis;

/**
 * @author: 胖虎
 * @date: 2019/7/1 16:08
 **/
public class OrderKey extends BasePrefix{

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey flashSaleOrderByUidAndGid = new OrderKey(0, "fs-order-uid-gid");
}
