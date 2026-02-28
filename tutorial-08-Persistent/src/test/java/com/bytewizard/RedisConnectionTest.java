package com.bytewizard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
public class RedisConnectionTest {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Test
    public void test() {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);  // 手动认证
        System.out.println("PING: " + jedis.ping());
        jedis.close();
    }
}
