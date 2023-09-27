package com.im.job.consumer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

/**
 * @Author : sharch
 * @create 2023/9/27 14:01
 */
@Configuration
@Slf4j
public class MessageReciver {

    public void kafkaConsumer(String topic, String groupId) {
        Properties props = new Properties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //消费者订阅主题
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(5000));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
                    Optional<?> kafkaMessage = Optional.ofNullable(record.value());
                    if (kafkaMessage.isPresent()) {
                        Object message = kafkaMessage.get();
                        JSONObject json = JSON.parseObject(message.toString());
                        //处理逻辑
                        //同步提交，当前线程会阻塞直到offset提交成功
                        consumer.commitSync();
                    }
                }
            }
        } finally {
            consumer.close();
        }

    }
}
