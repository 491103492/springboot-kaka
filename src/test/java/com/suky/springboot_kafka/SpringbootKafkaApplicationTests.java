package com.suky.springboot_kafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootKafkaApplicationTests {

    @Test
    void createTopic() {
        String topic = "test-topic";
        String command = "kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic " + topic;
        System.out.println(command);
    }

}
