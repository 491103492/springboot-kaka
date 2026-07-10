package com.suky.springboot_kafka.demo2;


import com.suky.springboot_kafka.model.KafkaModel;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> baseProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        return props;
    }

    @Bean
    public KafkaTemplate<String, KafkaModel> kafkaTemplate() {
        Map<String, Object> props = baseProducerConfigs();
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        ProducerFactory<String, KafkaModel> factory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(factory);
    }

    @Bean("acksAllKafkaTemplate")
    public KafkaTemplate<String, KafkaModel> acksAllKafkaTemplate() {
        Map<String, Object> props = baseProducerConfigs();
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        ProducerFactory<String, KafkaModel> factory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(factory);
    }
}