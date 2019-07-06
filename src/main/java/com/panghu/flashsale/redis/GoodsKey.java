package com.panghu.flashsale.redis;

/**
 * @author: 胖虎
 * @date: 2019/6/30 14:26
 **/
public class GoodsKey extends BasePrefix {

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey goodsList = new GoodsKey(60, "gl");
    public static GoodsKey goodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey flashSaleGoodsStock = new GoodsKey( "flgs");


}
