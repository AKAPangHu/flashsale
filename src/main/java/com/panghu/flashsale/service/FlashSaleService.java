package com.panghu.flashsale.service;

import com.panghu.flashsale.domain.OrderInfo;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.vo.GoodsVo;

/**
 * @author: 胖虎
 * @date: 2019/6/29 15:42
 **/
public interface FlashSaleService {

    OrderInfo rush(User user, GoodsVo goodsVo);

}
