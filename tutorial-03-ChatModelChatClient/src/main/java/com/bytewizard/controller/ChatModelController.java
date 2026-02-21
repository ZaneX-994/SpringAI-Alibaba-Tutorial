package com.bytewizard.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatModelController {

    @Autowired
    private ChatModel dashScopeChatModel;

    /* 错误示例: ChatClient不支持自动注入, 只能手动注入
    @Autowired
    private ChatClient chatClient;
    */

    @GetMapping("/chatmodel/dochat")
    public String chatModel(@RequestParam(name = "msg", defaultValue = "你是谁?") String msg) {

        return dashScopeChatModel.call(msg);

    }

}
