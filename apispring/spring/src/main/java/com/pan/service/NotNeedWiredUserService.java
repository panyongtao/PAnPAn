package com.pan.service;

import com.pan.AppConfig;
import lombok.Data;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author pan
 * @Date 2022/7/18 14:22
 * @Version 1.0
 * 属性不用注入也会有值
 */
@Data
public class NotNeedWiredUserService {
    private OrderService orderService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
        NotNeedWiredUserService bean = context.getBean(NotNeedWiredUserService.class);
        System.out.println(bean);
    }
}
