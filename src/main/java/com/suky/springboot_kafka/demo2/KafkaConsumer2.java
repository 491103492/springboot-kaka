package com.suky.springboot_kafka.demo2;

import com.suky.springboot_kafka.model.KafkaModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer2 {

    /**
     * 最简单的一个接收消息的方式
     * @param message
     */
    @KafkaListener(topics = "test-topic2", groupId = "my-group")
    public void listen(KafkaModel message) {
        System.out.println("Received Message: " + message);
        // 业务逻辑处理
    }
}
