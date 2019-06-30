package com.panghu.flashsale.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 秒杀商品类
 * @author: 胖虎
 * @date: 2019/6/27 15:58
 **/
@Setter
@Getter
@ToString
public class FlashSaleGoods {
    private long id;
    private long goodsId;
    private double promoPrice;
    private int stockCount;
    private Date startDate;
    private Date endDate;
}
