package com.panghu.flashsale.controller;

import com.panghu.flashsale.domain.FlashSaleOrder;
import com.panghu.flashsale.domain.OrderInfo;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.exception.GlobalException;
import com.panghu.flashsale.result.CodeMsg;
import com.panghu.flashsale.result.Result;
import com.panghu.flashsale.service.FlashSaleService;
import com.panghu.flashsale.service.GoodsService;
import com.panghu.flashsale.service.OrderService;
import com.panghu.flashsale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: 胖虎
 * @date: 2019/6/29 15:04
 **/
@Controller
public class FlashSaleController {

    private final GoodsService goodsService;

    private final OrderService orderService;

    private final FlashSaleService flashSaleService;

    @Autowired
    public FlashSaleController(GoodsService goodsService, OrderService orderService, FlashSaleService flashSaleService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
        this.flashSaleService = flashSaleService;
    }

    @RequestMapping(value = "/flashsale/rush", method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> rush(Model model, User user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if (stock <= 0) {
            throw new GlobalException(CodeMsg.FLASH_SALE_GOODS_STOCK_EMPTY);
        }

        FlashSaleOrder order = orderService.getFlashSaleOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            throw new GlobalException(CodeMsg.REPEAT_RUSH);
        }
        OrderInfo finishedOrder = flashSaleService.rush(user, goodsVo);
        return Result.success(finishedOrder);
    }
}
