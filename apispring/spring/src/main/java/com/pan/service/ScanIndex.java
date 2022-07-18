package com.pan.service;

import com.pan.ImportConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author pan
 * @Date 2022/7/18 16:49
 * @Version 1.0
 */
public class ScanIndex {
//    目前没有生效
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportConfig.class);
        User bean = context.getBean(User.class);
        System.out.println(bean);
        ScanIndex bean1 = context.getBean(ScanIndex.class);
        System.out.println(bean1);
    }
}
