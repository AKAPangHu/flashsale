package com.panghu.flashsale.vo;

import com.panghu.flashsale.domain.Goods;
import com.panghu.flashsale.domain.OrderInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: 胖虎
 * @date: 2019/7/1 14:32
 **/
@Setter
@Getter
@ToString
public class OrderDetailVo {

    OrderInfo order;

    GoodsVo goods;

}
