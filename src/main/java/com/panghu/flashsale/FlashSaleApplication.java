package com.panghu.flashsale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author: 胖虎
 * @date: 2019/6/16 21:12
 **/
@SpringBootApplication
public class FlashSaleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FlashSaleApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FlashSaleApplication.class);
    }
}
