package com.panghu.flashsale.controller;

import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.redis.GoodsKey;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.service.GoodsService;
import com.panghu.flashsale.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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

    private final RedisService redisService;

    private final ThymeleafViewResolver thymeleafViewResolver;

    public GoodsController(GoodsService goodsService, RedisService redisService, ThymeleafViewResolver thymeleafViewResolver) {
        this.goodsService = goodsService;
        this.redisService = redisService;
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(Model model, User user,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        if (user == null) {
            return "login";
        }
        //取缓存
        String html = redisService.get(GoodsKey.goodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //无缓存则查询数据库
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsVoList);

        //手动渲染
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.goodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(Model model, User user,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         @PathVariable("goodsId") long goodsId) {

        //取缓存
        //实际逻辑有问题！
        String html = redisService.get(GoodsKey.goodsDetail, Long.toString(goodsId), String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("user", user);
        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //0代表秒杀未开始，1代表秒杀进行中，2代表秒杀已结束
        int flashSaleStatus;
        int remainSeconds = 0;
        if (now < startTime) {
            remainSeconds = (int) ((startTime - now) / 1000);
            flashSaleStatus = 0;
        } else if (now > endTime) {
            flashSaleStatus = 2;
            remainSeconds = -1;
        } else {
            flashSaleStatus = 1;
        }

        model.addAttribute("flashSaleStatus", flashSaleStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        //手动渲染
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.goodsDetail, Long.toString(goodsId), html);
        }
        return html;
    }

}
