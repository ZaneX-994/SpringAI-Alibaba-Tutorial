package com.bytewizard.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfigPrinter implements ApplicationRunner {

    @Value("${spring.data.redis.host:NOT_SET}")
    private String redisHost;

    @Value("${spring.data.redis.port:NOT_SET}")
    private String redisPort;

    @Override
    public void run(ApplicationArguments args) {
        log.info("=== Redis 配置检查 ===");
        log.info("主机: {}", redisHost);
        log.info("端口: {}", redisPort);
    }
}
