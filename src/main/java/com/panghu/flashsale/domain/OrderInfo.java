package com.panghu.flashsale.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 订单信息类
 * @author: 胖虎
 * @date: 2019/6/27 16:01
 **/
@Setter
@Getter
@ToString
public class OrderInfo {
    private long id;
    private long userId;
    private long goodsId;
    private long deliveryAddrId;
    private String goodsName;
    private int goodsCount;
    private double goodsPrice;
    private int orderChannel;
    private int status;
    private Date createDate;
    private Date payDate;
}
