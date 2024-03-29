package com.pan.service;

import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author pan
 * @Date 2022/7/18 20:17
 * @Version 1.0
 * DeferredImportSelector 也行只是实现的时机不同
 */

public class ImportSelector implements org.springframework.context.annotation.ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //返回一个一个类的全名,Bean的名字是自动和类映射
        System.out.println(ImportSelectBean.class.getName());
        return new String[]{ImportSelectBean.class.getName()};
    }
}
