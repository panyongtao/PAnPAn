package com.pan.service;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Map;

/**
 * @Author pan
 * @Date 2022/7/18 20:17
 * @Version 1.0
 * DeferredImportSelector 也行只是实现的时机不同
 */

public class ImportSelectorRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanDefinition.setBeanClass(RegisterBean.class);
        registry.registerBeanDefinition(  "registerBean", beanDefinition);
    }
    //另类写法
    public void registerBeanDefinitions1(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //对注解进行扫描
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(ComponentScan.class.getName());
        //获取basePackages的值
        String[] basePackages = (String[])annotationAttributes.get("basePackages");

        //对类进行扫描
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry,false);
        TypeFilter helloUserFilter = new AssignableTypeFilter(User.class);
        scanner.addIncludeFilter(helloUserFilter);
        scanner.scan(basePackages);
    }

}
