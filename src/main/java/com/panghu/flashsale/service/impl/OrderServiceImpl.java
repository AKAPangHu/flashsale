package com.panghu.flashsale.service.impl;

import com.panghu.flashsale.dao.OrderDao;
import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.OrderInfo;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.redis.OrderKey;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.service.OrderService;
import com.panghu.flashsale.vo.GoodsVo;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 胖虎
 * @date: 2019/6/29 15:35
 **/
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    private final RedisService redisService;

    public OrderServiceImpl(OrderDao orderDao, RedisService redisService) {
        this.orderDao = orderDao;
        this.redisService = redisService;
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
        //这条语句执行后会自动将返回的参数传入orderInfo中去
        orderDao.insertOrder(orderInfo);

        return orderInfo;
    }

    @Override
    public FlashSaleOrder getFlashSaleOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return redisService.get(OrderKey.flashSaleOrderByUidAndGid,
                "" + userId + "_" + goodsId, FlashSaleOrder.class);
    }

    @Override
    public void createFlashSaleOrder(long userId, long goodsId, long ordersId) {
        FlashSaleOrder flashSaleOrder = new FlashSaleOrder();
        flashSaleOrder.setGoodsId(goodsId);
        flashSaleOrder.setUserId(userId);
        flashSaleOrder.setOrderId(ordersId);
        orderDao.insertFlashSaleOrder(flashSaleOrder);
        redisService.set(OrderKey.flashSaleOrderByUidAndGid,
                "" + userId + "_" + goodsId, flashSaleOrder);
    }

    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }
}
