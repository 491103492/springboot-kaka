package com.suky.springboot_kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送给kafka 的消息对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaModel implements java.io.Serializable{
    private String name;
    private Integer age;
    private String sex;
}
