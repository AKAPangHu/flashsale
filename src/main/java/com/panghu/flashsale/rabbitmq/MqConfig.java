package com.panghu.flashsale.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: 胖虎
 * @date: 2019/7/3 21:00
 **/
@Configuration
public class MqConfig {

    public static final String FLASH_SALE_QUEUE = "flashsale.queue";

    @Bean
    public Queue getFlashSaleQueue(){
        return new Queue(FLASH_SALE_QUEUE, true);
    }
}

