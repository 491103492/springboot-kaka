package com.suky.springboot_kafka.controller;

import com.suky.springboot_kafka.model.KafkaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaProducerController {
    @Autowired
    private KafkaTemplate<String, KafkaModel> kafkaTemplate;

    // 最简单的一个接收消息的方式
    private static final String TOPIC = "test-topic";

    // 顺序消费
    private static final String ORDER_TOPIC = "order-topic";


    /**
     * 发送消息(最简单的一个示例)
     * @param name
     * @return
     */
    @PostMapping("/send")
    public String sendMessage(@RequestParam String name) {
        // 发送消息到 Topic
        kafkaTemplate.send(TOPIC, "key-1", new KafkaModel(name, 18, "male"));
        return "Message sent: " + name;
    }
}
