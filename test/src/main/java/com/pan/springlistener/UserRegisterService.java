package com.pan.springlistener;


import cn.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService   {
    public void registerUser(){
        System.out.println("注册用户");
       SpringUtil.publishEvent(new MyEvent("test","pan"));
    }
}
