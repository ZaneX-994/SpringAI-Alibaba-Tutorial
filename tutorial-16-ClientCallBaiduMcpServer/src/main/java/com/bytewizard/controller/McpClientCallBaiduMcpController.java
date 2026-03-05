package com.bytewizard.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class McpClientCallBaiduMcpController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatModel chatModel;

    /*
    * http://localhost:8016/mcp/chat?msg=查询昌平到天安门路线规划
    * */
    @GetMapping("/mcp/chat")
    public Flux<String> chat(@RequestParam(name = "msg") String msg) {
        return chatClient.prompt(msg).stream().content();
    }

}
