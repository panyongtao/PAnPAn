package com.pan.service;

import com.pan.CommonConfig;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/18 20:46
 * @Version 1.0
 * 抽象类会忽略Component注解
 * @Component+@Lookup,这个抽象类就会变成一个Bean，而且是一个代理类
 */
@Component
public abstract class AbstractLookUp {
    @Lookup
    public void  test(){
        System.out.println("test");
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(CommonConfig.class);
        AbstractLookUp bean = context.getBean(AbstractLookUp.class);
        System.out.println(bean);
    }
}
