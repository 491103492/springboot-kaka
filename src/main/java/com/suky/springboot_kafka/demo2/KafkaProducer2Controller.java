package com.suky.springboot_kafka.demo2;

import com.suky.springboot_kafka.model.KafkaModel;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka2")
public class KafkaProducer2Controller {

    public static final String TOPIC = "test-topic2";

    @Resource
    private KafkaTemplate<String, KafkaModel> kafkaTemplate;

    @Resource
    @Qualifier("acksAllKafkaTemplate")
    private KafkaTemplate<String, KafkaModel> acksAllKafkaTemplate;

    @RequestMapping("/send")
    public String sendMessage(@RequestParam String name) {
        // 默认发送策略
        kafkaTemplate.send(TOPIC, "key-1", new KafkaModel(name, 18, "male"));
        // 确保所有副本都成功存储
        SendResult<String, KafkaModel> male = acksAllKafkaTemplate.send(TOPIC, "key-2", new KafkaModel(name, 18, "male")).join();

        return "Message sent: " + name;
    }



}
