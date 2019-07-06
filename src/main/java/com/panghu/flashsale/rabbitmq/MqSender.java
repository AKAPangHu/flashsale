package com.panghu.flashsale.rabbitmq;

import com.panghu.flashsale.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 胖虎
 * @date: 2019/7/3 21:06
 **/
@Service
public class MqSender {

    private final AmqpTemplate amqpTemplate;

    private final Logger logger = LoggerFactory.getLogger(MqSender.class);

    @Autowired
    public MqSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void send(FlashSaleMessage message){
        String messageString = RedisService.beanToString(message);
        logger.info(messageString);
        amqpTemplate.convertAndSend(MqConfig.FLASH_SALE_QUEUE, messageString);
    }
}
