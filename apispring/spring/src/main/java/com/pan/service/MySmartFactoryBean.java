package com.pan.service;

import com.pan.ImportConfig;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/18 21:21
 * @Version 1.0
 * 同时也要交给Spring 管理
 */
@Component
public class MySmartFactoryBean implements SmartFactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new FactoryBeanInst();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBeanInst.class;
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportConfig.class);
        FactoryBeanInst bean = (FactoryBeanInst) context.getBean("mySmartFactoryBean");
        System.out.println(bean);
    }
    /**控制是不是饥饿加载默认false，true的话马上加载*/
    @Override
    public boolean isEagerInit() {
        return false;
    }
}
