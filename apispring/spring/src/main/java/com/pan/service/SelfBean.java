package com.pan.service;

import com.pan.AppConfig;
import com.pan.service.ano.MyComponent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author pan
 * @Date 2022/7/18 16:29
 * @Version 1.0
 *
 * 自定义注解如果也想把对象放到spring容器当中需要和@ComponentScan
 * 注解配合使用
 */
@MyComponent
public class SelfBean {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
        SelfBean bean = context.getBean(SelfBean.class);
        System.out.println(bean);
    }
}
