package com.pan.service.postproccessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/19 14:06
 * @Version 1.0
 * 每个bean都会调用
 */
@Component
public class MyBeanPostProccessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("bean初始化器之前"+beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("bean初始化器之后");
        return null;
    }
}
