package com.im.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImJobApplication {
    // todo 消息消费 kafka
    public static void main(String[] args) {
        SpringApplication.run(ImJobApplication.class, args);
    }

}
