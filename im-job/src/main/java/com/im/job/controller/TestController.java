package com.im.job.controller;

import cn.hutool.core.date.DateUtil;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : sharch
 * @create 2023/9/26 16:51
 */
@RestController
@RequestMapping("/kafka")
public class TestController {
    @Autowired
    KafkaTemplate<String, String> kafka;

    @GetMapping("ping")
    public ResponseEntity Ping() {
        String time = String.valueOf(DateUtil.now());
        kafka.send("test", time);
        return ResponseEntity.ok("ok");
    }
}
