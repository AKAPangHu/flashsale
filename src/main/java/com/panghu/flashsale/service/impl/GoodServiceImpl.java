package com.panghu.flashsale.service.impl;

import com.panghu.flashsale.dao.GoodsDao;
import com.panghu.flashsale.domain.FlashSaleGoods;
import com.panghu.flashsale.service.GoodsService;
import com.panghu.flashsale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 胖虎
 * @date: 2019/6/27 19:44
 **/
@Service
public class GoodServiceImpl implements GoodsService {

    private final GoodsDao goodsDao;

    @Autowired
    public GoodServiceImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }


    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public void reduceStock(FlashSaleGoods flashSaleGoods) {
        goodsDao.reduceStock(flashSaleGoods);
    }
}
