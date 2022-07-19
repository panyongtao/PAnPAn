package com.pan.service.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/19 14:11
 * @Version 1.0
 */
@Component
public class MyApplcationAware implements ApplicationContextAware {
    //也可以获取到上文问对象，两种获取方式都可以
    @Autowired
    private ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("获取了spring aware属性");
    }
}
