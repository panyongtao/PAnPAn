package com.pan.service.ano;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author pan
 * @Date 2022/7/18 15:13
 * @Version 1.0
 * 多例bean注解
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Bean
@Scope("prototype")
public @interface PrototypeBean {
}
