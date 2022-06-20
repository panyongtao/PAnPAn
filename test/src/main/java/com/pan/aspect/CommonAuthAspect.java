package com.pan.aspect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class CommonAuthAspect {
    @Autowired
    private HttpServletRequest request;

    private static String key="key";
    private static String secrect="secrect";

    @Pointcut("@annotation(com.pan.annotation.CommonAuth) || @within(com.pan.annotation.CommonAuth)")
    public void pointCut() {

    }

    @Around("pointCut()")
    @SneakyThrows
    public Object doAround(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature() ;
        Method method = methodSignature.getMethod() ;
        String systemId = request.getHeader(key) ;
        String secrecy = request.getHeader(secrect) ;
        System.out.println(request.getQueryString());
        Object id = request.getAttribute("id");
        System.out.println(id);
        System.out.println("请求地址"+request.toString());
        //判断
        return pjp.proceed();
    }
}

