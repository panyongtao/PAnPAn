package com.pan;

import com.pan.service.OrderService;
import com.pan.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author pan
 * @Date 2022/7/18 14:22
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportConfig.class);
        System.out.println(context.getBean(UserService.class));
        System.out.println(context.getBean(OrderService.class));
    }
}
