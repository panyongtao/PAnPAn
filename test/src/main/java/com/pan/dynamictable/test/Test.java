package com.pan.dynamictable.test;

import com.pan.Application;
import com.pan.dynamictable.DynamicService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class Test {
    @Autowired
    private  DynamicService dynamicService;
    @org.junit.Test
    public void test(){
        System.out.println(dynamicService);
    }
}
