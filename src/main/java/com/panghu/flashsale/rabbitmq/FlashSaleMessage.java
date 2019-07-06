package com.panghu.flashsale.rabbitmq;

import com.panghu.flashsale.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: 胖虎
 * @date: 2019/7/6 16:45
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlashSaleMessage implements Serializable {

    private long goodsId;

    private User user;
}
