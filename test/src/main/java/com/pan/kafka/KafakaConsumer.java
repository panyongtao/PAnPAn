package com.pan.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafakaConsumer {
    @KafkaListener(topics = {"hello2"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional.ofNullable(record.value())
                .ifPresent(message -> {
                    log.info("【接收 record = {} 】", record);
                    log.info("【接收 message = {}】", message);
                });
    }

}
