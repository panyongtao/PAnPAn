//package com.pan.datasource.aspect;
//
//import com.pan.datasource.DynamicDataSource;
//import com.pan.datasource.annotion.DS;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@Slf4j
//public class DynamicSourceAspect {
//    @Pointcut("@annotation(com.pan.datasource.annotion.DS) || @within(com.pan.datasource.annotion.DS)")
//    public void pointCut() {
//
//    }
//    @SneakyThrows
//    @Before("pointCut()")
//    public void doBefore(JoinPoint pjp) {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        DS annotation = signature.getMethod().getAnnotation(DS.class);
//        String name = annotation.value();
//        System.out.println("数据源切面值"+name);
//        DynamicDataSource.datasourceName.set(name);
//    }
//}
//
