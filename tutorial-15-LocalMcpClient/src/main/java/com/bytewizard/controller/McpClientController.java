package com.bytewizard.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class McpClientController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatModel ChatModel;
    @Autowired
    private ChatModel chatModel;

    @GetMapping("/mcpclient/chat")
    public Flux<String> chat(@RequestParam(name = "msg", defaultValue = "北京") String msg) {
        System.out.println("使用了MCP");
        return chatClient.prompt(msg).stream().content();
    }

    @GetMapping("/mcpclient/chat2")
    public Flux<String> chat2(@RequestParam(name = "msg", defaultValue = "北京") String msg) {
        System.out.println("未使用了MCP");
        return chatModel.stream(msg);
    }


}
