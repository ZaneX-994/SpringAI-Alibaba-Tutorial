package com.bytewizard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
public class RedisConnectionTest {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() {
        Jedis jedis = new Jedis(host, port);
        System.out.println("PING: " + jedis.ping());
        jedis.close();
    }

    @Test
    public void test2() {
        System.out.println(redisTemplate.opsForList().getFirst("spring_ai_alibaba_chat_memory:1"));

    }
}
