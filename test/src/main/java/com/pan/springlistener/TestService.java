package com.pan.springlistener;

import com.pan.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestService {
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserRegisterService1 userRegisterService1;
    @Autowired
    private UserRegisterService2 userRegisterService2;
    @Autowired
    private UserRegisterService3 userRegisterService3;
    @Test
    public void test(){
        userRegisterService.registerUser();
    }
    @Test
    public void test1(){
        userRegisterService1.registerUser();
    }
    @Test
    public void test2(){
        userRegisterService2.registerUser();
    }
    @Test
    public void test3(){
        userRegisterService3.registerUser();
    }
}
