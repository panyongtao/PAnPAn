package com.pan.service;

import com.pan.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author pan
 * @Date 2022/7/18 16:02
 * @Version 1.0
 */
public class PrototypeBean {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getBean(PrototypeBean.class));
        System.out.println(context.getBean(PrototypeBean.class));
        System.out.println(context.getBean(PrototypeBean.class));
    }
}
