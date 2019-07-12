package com.panghu.flashsale.controller;

import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.exception.GlobalException;
import com.panghu.flashsale.rabbitmq.FlashSaleMessage;
import com.panghu.flashsale.rabbitmq.MqSender;
import com.panghu.flashsale.redis.FlashSaleKey;
import com.panghu.flashsale.redis.GoodsKey;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.result.CodeMsg;
import com.panghu.flashsale.result.Result;
import com.panghu.flashsale.service.FlashSaleService;
import com.panghu.flashsale.service.GoodsService;
import com.panghu.flashsale.service.OrderService;
import com.panghu.flashsale.utils.MD5Utils;
import com.panghu.flashsale.utils.UUIDUtils;
import com.panghu.flashsale.vo.GoodsVo;
import com.sun.tools.javac.jvm.Code;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author: 胖虎
 * @date: 2019/6/29 15:04
 **/
@Controller
@RequestMapping("/flashsale")
public class FlashSaleController implements InitializingBean {

    private final GoodsService goodsService;

    private final OrderService orderService;

    private final RedisService redisService;

    private final FlashSaleService flashSaleService;

    private final MqSender mqSender;

    public FlashSaleController(GoodsService goodsService, OrderService orderService, RedisService redisService, FlashSaleService flashSaleService, MqSender mqSender) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.redisService = redisService;
        this.flashSaleService = flashSaleService;
        this.mqSender = mqSender;
    }

    private HashMap<Long, Boolean> localOverMap = new HashMap<>();


    @Override
    public void afterPropertiesSet() {
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        goodsVos.forEach(goodsVo -> {
            localOverMap.put(goodsVo.getId(), false);
            redisService.set(GoodsKey.flashSaleGoodsStock, ""+goodsVo.getId(), goodsVo.getStockCount());
        });
    }


    @RequestMapping(value = "/rush/{path}", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> rush(User user,
                                @RequestParam("goodsId") long goodsId,
                                @PathVariable String path) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        boolean valid = flashSaleService.checkPath(path, user, goodsId);

        if (!valid){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //查看是否秒杀已结束
        Boolean over = localOverMap.get(goodsId);
        if (over == null){
            return Result.error(CodeMsg.FLASH_SALE_IS_OVER);
        }
        else if (over){
            return Result.error(CodeMsg.FLASH_SALE_GOODS_STOCK_EMPTY);
        }
        //预减库存
        //我感觉实际逻辑会有问题，虽然不会卖超，但会出现订单不足的情况
        redisService.decr(GoodsKey.flashSaleGoodsStock, ""+goodsId);
        int stock = redisService.get(GoodsKey.flashSaleGoodsStock, ""+goodsId, int.class);

        if (stock < 0){
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.FLASH_SALE_GOODS_STOCK_EMPTY);
        }
        //检查之前是否秒杀过了
        FlashSaleOrder order = orderService.getFlashSaleOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_RUSH);
        }

        //入队
        FlashSaleMessage flashSaleMessage = new FlashSaleMessage(goodsId, user);
        mqSender.send(flashSaleMessage);
        //返回排队中
        return Result.success(0);
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> getResult(User user,@RequestParam("goodsId") long goodsId){
        long result = flashSaleService.getResult(user.getId(), goodsId);
        return Result.success(result);
    }

    @RequestMapping(value = "/path")
    @ResponseBody
    public Result<String> getPath(User user, @RequestParam String goodsId){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //生成path，存入redis
        String path = MD5Utils.md5(UUIDUtils.uuid() + "panghu");
        redisService.set(FlashSaleKey.flashSalePath, "" + user.getId() + "_" + goodsId, path);
        return Result.success(path);
    }


    @RequestMapping(value = "/reset")
    public void reset(){
        orderService.reset();
    }

}
