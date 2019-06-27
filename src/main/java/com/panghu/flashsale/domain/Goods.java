package com.panghu.flashsale.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品类
 * @author: 胖虎
 * @date: 2019/6/26 21:33
 **/
@Getter
@Setter
@ToString
public class Goods {
    private long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private double goodsPrice;
    private int goodsStock;
}
