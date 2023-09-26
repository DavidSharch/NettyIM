package com.im.job.consumer;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @Author : sharch
 * @create 2023/9/26 16:55
 */
@Configuration
public class TestConsumer {
    @KafkaListener(topics = "test")
    public void consume(String message) {
        System.out.println("接收到消息：" + message);
    }
}
