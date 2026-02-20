package com.bytewizard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class configTest {

    @Value("${spring.ai.dashscope.api-key}")
    String apikey;

    @Test
    public void contextLoads() {
        System.out.println(apikey);
    }

}
