package com.fmjava.service;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class TestInterfaceImpl implements TestInterface {
    @Override
    public String getName() {
        return "fmjava dubbo -----";
    }
}
