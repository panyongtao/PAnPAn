package com.pan.springlistener;


import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserRegisterService2 extends PulishAware {

    public void registerUser(){
        System.out.println("注册用户");
        publisher.publishEvent(new MyEvent("test","pan"));
    }
}
