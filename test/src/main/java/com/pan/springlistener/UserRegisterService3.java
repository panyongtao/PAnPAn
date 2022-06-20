package com.pan.springlistener;


import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserRegisterService3  {

    public void registerUser(){
        System.out.println("注册用户");
        PulishAware.publisher.publishEvent(new MyEvent("test","pan"));
    }
}
