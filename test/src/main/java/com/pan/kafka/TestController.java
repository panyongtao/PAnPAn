package com.pan.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @Autowired
    private KafkaSender kafkaSender;

    @GetMapping("sendMessage")
    public void sendMessage(String msg){
        kafkaSender.send(msg);
    }
}
