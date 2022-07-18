package com.pan.service;

import com.pan.ImportConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/18 21:21
 * @Version 1.0
 * 同时也要交给Spring 管理
 */
@Component
public class MyFactoryBean implements FactoryBean {
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
        FactoryBeanInst bean = (FactoryBeanInst) context.getBean("myFactoryBean");
        System.out.println(bean);
    }
}
