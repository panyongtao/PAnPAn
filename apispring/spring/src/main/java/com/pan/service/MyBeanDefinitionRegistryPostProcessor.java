package com.pan.service;

import com.pan.CommonConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/18 21:08
 * @Version 1.0
 * BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 * BeanFactoryPostProcessor只能获取Bean
 * 子接口就可以实现一些方法
 * 此Processor也要交给Spring管理
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    public static void main(String[] args) {
        //哪怕AppConfig不加任何注解都行 但只限于本类
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(CommonConfig.class);
        RegisterPostProcessorBean bean = (RegisterPostProcessorBean) context.getBean("registerPostProcessorBean");
        System.out.println(bean);
    }
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanDefinition.setBeanClass(RegisterPostProcessorBean.class);
        beanDefinitionRegistry.registerBeanDefinition(  "registerPostProcessorBean", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
    class A{

    }
}
