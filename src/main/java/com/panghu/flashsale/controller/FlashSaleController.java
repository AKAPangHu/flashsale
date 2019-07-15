package com.panghu.flashsale.controller;

import com.panghu.flashsale.access.AccessLimit;
import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.User;
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
import com.panghu.flashsale.utils.CAPTCHAUtils;
import com.panghu.flashsale.utils.MD5Utils;
import com.panghu.flashsale.utils.UUIDUtils;
import com.panghu.flashsale.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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

    private final CAPTCHAUtils captchaUtils;

    public FlashSaleController(GoodsService goodsService, OrderService orderService, RedisService redisService, FlashSaleService flashSaleService, MqSender mqSender, CAPTCHAUtils captchaUtils) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.redisService = redisService;
        this.flashSaleService = flashSaleService;
        this.mqSender = mqSender;
        this.captchaUtils = captchaUtils;
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


    @AccessLimit(seconds = 60)
    @RequestMapping(value = "/rush/{path}", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> rush(User user,
                                @RequestParam("goodsId") long goodsId,
                                @RequestParam("captcha") String captcha,
                                @PathVariable String path) {
        //检验路径
        boolean valid = flashSaleService.checkPath(path, user, goodsId);

        if (!valid){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //检查验证码
        boolean right = flashSaleService.checkCaptcha(captcha, user.getId(), goodsId);
        if (!right){
            return Result.error(CodeMsg.CAPTCHA_ERROR);
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
    public Result<String> getPath(User user, @RequestParam long goodsId){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        String path = redisService.get(FlashSaleKey.flashSalePath, "" + user.getId() + "_" + goodsId, String.class);
        if (path != null){
            return Result.success(path);
        }
        //生成path，存入redis
        path = MD5Utils.md5(UUIDUtils.uuid() + "panghu");
        redisService.set(FlashSaleKey.flashSalePath, "" + user.getId() + "_" + goodsId, path);
        return Result.success(path);
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getCaptcha(User user, @RequestParam long goodsId,
                                     HttpServletResponse response) throws ScriptException {
        //生成验证码并存储，返回一个图像
        BufferedImage bufferedImage = captchaUtils.generateCaptcha(user.getId(), goodsId);
        //返回验证码
        try(OutputStream out = response.getOutputStream()) {
            ImageIO.write(bufferedImage, "JPEG", out);
            out.flush();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/reset")
    public void reset(){
        orderService.reset();
    }

}
