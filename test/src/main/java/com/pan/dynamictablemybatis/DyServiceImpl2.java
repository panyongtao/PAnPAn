package com.pan.dynamictablemybatis;


import org.springframework.stereotype.Service;

/**
 * 其实这个动态表查询和mybatis没关系
 */
@Service("dynamicService2")
public class DyServiceImpl2 implements DyService{
    public String f(){
        System.out.println("panpan");
        return "av";
    }
}
