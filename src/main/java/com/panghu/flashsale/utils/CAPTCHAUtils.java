package com.panghu.flashsale.utils;

import com.panghu.flashsale.redis.FlashSaleKey;
import com.panghu.flashsale.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author: 胖虎
 * @date: 2019/7/13 14:05
 **/
@Component
public class CAPTCHAUtils {

    private final RedisService redisService;

    private static final int WIDTH = 80;
    private static final int HEIGHT = 32;
    private static final int CONFUSION_FREQUENCY = 50;

    private static final char[] OPERATIONS = {'+', '-', '*'};

    public CAPTCHAUtils(RedisService redisService) {
        this.redisService = redisService;
    }

    public BufferedImage generateCaptcha(long userId, long goodsId) throws ScriptException {
        //create the image
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < CONFUSION_FREQUENCY; i++) {
            int x = rdm.nextInt(WIDTH);
            int y = rdm.nextInt(HEIGHT);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String code = generateCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(code, 8, 24);
        g.dispose();

        //计算验证码
        int result = calculate(code);
        //存储验证码
        redisService.set(FlashSaleKey.flashSaleCaptcha, userId + "_" + goodsId, result);
        return image;
    }

    private String generateCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = OPERATIONS[rdm.nextInt(3)];
        char op2 = OPERATIONS[rdm.nextInt(3)];
        return "" + num1 + op1 + num2 + op2 + num3;
    }

    private int calculate (String code) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        return (int)scriptEngine.eval(code);
    }
}
