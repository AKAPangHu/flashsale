package com.panghu.flashsale.service;

import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.OrderInfo;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.vo.GoodsVo;

/**
 * @author: 胖虎
 * @date: 2019/6/29 15:27
 **/
public interface OrderService {


    OrderInfo createOrder(User user, GoodsVo goodsVo);

    FlashSaleOrder getFlashSaleOrderByUserIdAndGoodsId(long userId, long goodsId);

    void createFlashSaleOrder(long userId, long goodsId, long ordersId);

    OrderInfo getOrderById(long orderId);
}
