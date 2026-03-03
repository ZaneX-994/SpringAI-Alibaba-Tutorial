package com.bytewizard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AIWorkHelperApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIWorkHelperApplication.class, args);
        System.out.println("==============================================");
        System.out.println("AIWorkHelper 启动成功!");
        System.out.println("==============================================");
    }
}
