package com.fmjava.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.service.TestInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Reference
    TestInterface testInterface;

    @RequestMapping("/getname")
    public String getName(){
        String name = testInterface.getName();
        return name;
    }
}
