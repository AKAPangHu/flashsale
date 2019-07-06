package com.panghu.flashsale.dao;

import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: 胖虎
 * @date: 2019/6/29 15:35
 **/
@Mapper
@Repository
public interface OrderDao {
    @Select("select * from flashsale_order where user_id = #{userId} and goods_id = #{goodsId}")
    FlashSaleOrder getFlashSaleOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId")long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    long insertOrder(OrderInfo orderInfo);

    @Insert("insert into flashsale_order(user_id, goods_id, order_id) values(#{userId}, #{goodsId}, #{orderId})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    void insertFlashSaleOrder(FlashSaleOrder flashSaleOrder);

    @Select("select * from order_info where id = #{id}")
    OrderInfo getOrderById(@Param("id") long id);

    @Delete("delete from order_info")
    void deleteOrderInfos();

    @Delete("delete from flashsale_order")
    void deleteFlashSaleOrder();
}
