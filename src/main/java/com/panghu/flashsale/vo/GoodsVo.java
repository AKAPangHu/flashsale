package com.panghu.flashsale.vo;

import com.panghu.flashsale.domain.Goods;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author: 胖虎
 * @date: 2019/6/27 16:49
 **/
@Getter
@Setter
@ToString(callSuper = true)
public class GoodsVo extends Goods {
    private double promoPrice;
    private int stockCount;
    private Date startDate;
    private Date endDate;
}
