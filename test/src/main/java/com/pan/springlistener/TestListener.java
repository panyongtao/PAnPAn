package com.pan.springlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener {
    @EventListener
    public void sendMail(MyEvent event){
        System.out.println("发送邮件");
    }
}
