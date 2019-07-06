package com.panghu.flashsale.service.impl;

import com.panghu.flashsale.domain.FlashSaleGoods;
import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.OrderInfo;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.redis.FlashSaleKey;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.service.FlashSaleService;
import com.panghu.flashsale.service.GoodsService;
import com.panghu.flashsale.service.OrderService;
import com.panghu.flashsale.vo.GoodsVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 胖虎
 * @date: 2019/6/29 15:43
 **/
@Service
public class FlashSaleServiceImpl implements FlashSaleService {

    private final GoodsService goodsService;

    private final OrderService orderService;

    private final RedisService redisService;

    public FlashSaleServiceImpl(GoodsService goodsService, OrderService orderService, RedisService redisService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.redisService = redisService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo rush(User user, GoodsVo goodsVo) {
        FlashSaleGoods flashSaleGoods = new FlashSaleGoods();
        flashSaleGoods.setGoodsId(goodsVo.getId());
        boolean success = goodsService.reduceStock(flashSaleGoods);
        if (!success){
            setOver(goodsVo.getId());
            return null;
        }
        OrderInfo orderInfo = orderService.createOrder(user, goodsVo);
        orderService.createFlashSaleOrder(user.getId(), goodsVo.getId(), orderInfo.getId());
        return orderInfo;
    }

    @Override
    public long getResult(long userId, long goodsId){
        FlashSaleOrder order = orderService.getFlashSaleOrderByUserIdAndGoodsId(userId, goodsId);
        if (order != null){
            return order.getOrderId();
        }
        else{
            boolean isOver = getOver(goodsId);
            if (isOver){
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    private void setOver(long goodsId){
        redisService.set(FlashSaleKey.isOver, "" + goodsId, true);
    }

    private boolean getOver(long goodsId){
        return redisService.exists(FlashSaleKey.isOver, "" + goodsId);
    }

}
