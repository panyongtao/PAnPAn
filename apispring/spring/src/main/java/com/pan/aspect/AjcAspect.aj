package com.pan.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Author pan
 * @Date 2022/8/4 11:21
 * @Version 1.0
 */
@Aspect
public class AjcAspect {
    @Before("execution (* com.pan.aspect.AspectService.foo())")
    public void before(){
        System.out.println("代理before");
    }


}
