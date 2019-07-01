package com.panghu.flashsale.vo;

import com.panghu.flashsale.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: 胖虎
 * @date: 2019/6/30 21:38
 **/
@Setter
@Getter
@ToString
public class GoodsDetailVo {

    private GoodsVo goods;

    /**
     * 0代表秒杀未开始，1代表秒杀进行中，2代表秒杀已结束
     */
    private int flashSaleStatus;

    private int remainSeconds;

    private User user;
}
