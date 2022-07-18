package com.pan.service;

import com.pan.ImportConfig;
import com.pan.service.ano.MyComponent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @Author pan
 * @Date 2022/7/18 19:45
 * @Version 1.0
 * 导入Bean仍然可以
 */
@ComponentScan(value = "com.pan",
        includeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION,value=MyComponent.class))
public class ImportUser {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportConfig.class);
        ImportUser bean = context.getBean(ImportUser.class);
        System.out.println(bean);
    }
}
