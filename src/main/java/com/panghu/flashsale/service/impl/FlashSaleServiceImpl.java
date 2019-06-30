package com.panghu.flashsale.service.impl;

import com.panghu.flashsale.domain.FlashSaleGoods;
import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.OrderInfo;
import com.panghu.flashsale.domain.User;
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

    public FlashSaleServiceImpl(GoodsService goodsService, OrderService orderService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo rush(User user, GoodsVo goodsVo) {
        FlashSaleGoods flashSaleGoods = new FlashSaleGoods();
        flashSaleGoods.setGoodsId(goodsVo.getId());
        goodsService.reduceStock(flashSaleGoods);
        OrderInfo orderInfo = orderService.createOrder(user, goodsVo);
        orderService.createFlashSaleOrder(user.getId(), goodsVo.getId(), orderInfo.getId());
        return orderInfo;
    }
}
