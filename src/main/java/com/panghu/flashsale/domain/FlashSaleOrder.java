package com.panghu.flashsale.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 秒杀订单类
 * @author: 胖虎
 * @date: 2019/6/27 16:07
 **/
@Setter
@Getter
@ToString
public class FlashSaleOrder {
    private long id;
    private long userId;
    private long goodsId;
    private long orderId;
}
