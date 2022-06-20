package com.pan.springlistener;


import lombok.Data;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserRegisterService1 implements ApplicationEventPublisherAware {

      ApplicationEventPublisher applicationEventPublisher;
//     //获取spring的发布器
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher=applicationEventPublisher;
    }
    public void registerUser(){
        System.out.println("注册用户");
        applicationEventPublisher.publishEvent(new MyEvent("test","pan"));
    }
}
