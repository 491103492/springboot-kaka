package com.suky.springboot_kafka.demo1.controller;

import com.suky.springboot_kafka.model.KafkaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/kafka")
public class KafkaProducerController {
    @Autowired
    private KafkaTemplate<String, KafkaModel> kafkaTemplate;

    // 最简单的一个接收消息的方式
    private static final String TOPIC = "test-topic";



    /**
     * 发送消息(最简单的一个示例)
     * 默认为自动分区策率
     * @param name
     * @return
     */
    @PostMapping("/send")
    public String sendMessage(@RequestParam String name) {
        // 发送消息到 Topic
        CompletableFuture<SendResult<String, KafkaModel>> male = kafkaTemplate.send(TOPIC,  new KafkaModel(name, 18, "male"));

        male.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("发送成功: " + result);
            } else {
                System.out.println("发送失败: " + ex.getMessage());
            }
        });
        return "Message sent: " + name;
    }

    /**
     * 无参数发送消息
     * key 是为了分区所用。Map 取模的方式
     */
    @PostMapping("/sendNoParam")
    public String sendMessageNoParam() {
        KafkaModel defaultModel = new KafkaModel("default", 0, "unknown");
        kafkaTemplate.send(TOPIC, "key-default", defaultModel);
        return "Message sent";
    }

    /**
     * 同步发送消息，等待所有副本存储成功后返回
     */
    @PostMapping("/sendSync")
    public String sendMessageSync(@RequestParam String name) throws ExecutionException, InterruptedException {
        KafkaModel model = new KafkaModel(name, 18, "male");
        CompletableFuture<SendResult<String, KafkaModel>> future = kafkaTemplate.send(TOPIC, "key-sync", model);
        SendResult<String, KafkaModel> result = future.get(); // 等待所有执行结束
        // 主要依靠 acks=all
        return "Message sent and all replicas acknowledged: " + result.getRecordMetadata();
    }


}
