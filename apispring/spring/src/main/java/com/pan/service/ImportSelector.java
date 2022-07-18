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
        //返回一个Bean名字
        return new String[]{ImportSelectBean.class.getName()};
    }
}
