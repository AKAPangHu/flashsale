package com.panghu.flashsale.controller;

import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.service.GoodsService;
import com.panghu.flashsale.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: 胖虎
 * @date: 2019/6/25 19:28
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @RequestMapping("/to_list")
    public String list(Model model,
                          User user,
                          HttpServletResponse response) {
        if (user == null) {
            return "login";
        }

        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();

        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsVoList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(User user, Model model, @PathVariable("goodsId") long goodsId){

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("user", user);
        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //0代表秒杀未开始，1代表秒杀进行中，2代表秒杀已结束
        int flashSaleStatus;
        int remainSeconds = 0;
        if (now < startTime){
            remainSeconds = (int) ((startTime - now) / 1000);
            flashSaleStatus = 0;
        }
        else if (now > endTime){
            flashSaleStatus = 2;
            remainSeconds = -1;
        }
        else{
            flashSaleStatus = 1;
        }

        model.addAttribute("flashSaleStatus", flashSaleStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";
    }

}
