package com.panghu.flashsale.rabbitmq;

import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.service.FlashSaleService;
import com.panghu.flashsale.service.GoodsService;
import com.panghu.flashsale.service.OrderService;
import com.panghu.flashsale.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author: 胖虎
 * @date: 2019/7/3 21:06
 **/
@Service
public class MqReceiver {

    private final Logger logger = LoggerFactory.getLogger(MqReceiver.class);

    private final GoodsService goodsService;

    private final OrderService orderService;

    private final FlashSaleService flashSaleService;

    public MqReceiver(GoodsService goodsService, OrderService orderService, FlashSaleService flashSaleService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.flashSaleService = flashSaleService;
    }

    @RabbitListener(queues = MqConfig.FLASH_SALE_QUEUE)
    public void receive(String message){
        logger.info("receive message:" + message);
        FlashSaleMessage flashSaleMessage = RedisService.stringToBean(message, FlashSaleMessage.class);
        long goodsId = flashSaleMessage.getGoodsId();
        User user = flashSaleMessage.getUser();
        //判断是否还有库存，真正的数据库中
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if (stock <= 0){
            return;
        }
        //判断是否重复秒杀
        FlashSaleOrder order = orderService.getFlashSaleOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null){
            return;
        }
        //秒杀
        flashSaleService.rush(user, goodsVo);
    }
}
