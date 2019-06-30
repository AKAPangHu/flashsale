package com.panghu.flashsale.service.impl;

import com.panghu.flashsale.dao.OrderDao;
import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.OrderInfo;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.service.OrderService;
import com.panghu.flashsale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 胖虎
 * @date: 2019/6/29 15:35
 **/
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public OrderInfo createOrder(User user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setDeliveryAddrId(0);
        orderInfo.setCreateDate(new Date());
        orderInfo.setStatus(0);
        orderInfo.setOrderChannel(0);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(goodsVo.getPromoPrice());
        long orderId = orderDao.insertOrder(orderInfo);
        orderInfo.setId(orderId);
        return orderInfo;
    }

    @Override
    public FlashSaleOrder getFlashSaleOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return orderDao.getFlashSaleOrderByUserIdAndGoodsId(userId, goodsId);
    }

    @Override
    public void createFlashSaleOrder(long userId, long goodsId, long ordersId) {
        FlashSaleOrder flashSaleOrder = new FlashSaleOrder();
        flashSaleOrder.setGoodsId(goodsId);
        flashSaleOrder.setUserId(userId);
        flashSaleOrder.setOrderId(ordersId);
        orderDao.insertFlashSaleOrder(flashSaleOrder);
    }
}
