package com.bytewizard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        System.out.println("=== 测试端点被调用 ===");
        return "OK";
    }

    @PostMapping("/test-post")
    public String testPost(@RequestBody Map<String, Object> body) {
        System.out.println("=== 测试POST端点被调用: " + body);
        return "OK";
    }
}
