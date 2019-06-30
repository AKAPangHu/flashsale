package com.panghu.flashsale.service;

import com.panghu.flashsale.domain.FlashSaleGoods;
import com.panghu.flashsale.vo.GoodsVo;

import java.util.List;

/**
 * @author: 胖虎
 * @date: 2019/6/27 19:43
 **/
public interface GoodsService {
    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    void reduceStock(FlashSaleGoods flashSaleGoods);
}
