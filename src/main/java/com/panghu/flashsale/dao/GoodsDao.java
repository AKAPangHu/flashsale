package com.panghu.flashsale.dao;

import com.panghu.flashsale.domain.FlashSaleGoods;
import com.panghu.flashsale.domain.Goods;
import com.panghu.flashsale.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 胖虎
 * @date: 2019/6/27 17:22
 **/
@Mapper
@Repository
public interface GoodsDao {

    @Select("select g.*, fg.promo_price, fg.stock_count, fg.start_date, fg.end_date " +
            "from flashsale_goods fg left join goods g on " +
            "fg.goods_id = g.id ")
    List<GoodsVo> listGoodsVo();
    @Select("select g.*, fg.promo_price, fg.stock_count, fg.start_date, fg.end_date " +
            "from flashsale_goods fg left join goods g on " +
            "fg.goods_id = g.id where goods_id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update flashsale_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    int reduceStock(FlashSaleGoods flashSaleGoods);
}
