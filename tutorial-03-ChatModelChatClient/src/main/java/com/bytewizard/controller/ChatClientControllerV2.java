package com.bytewizard.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatClientControllerV2 {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatModel chatModel;

    @GetMapping("/chatclientv2/dochat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁?") String msg) {

        return chatClient.prompt().user(msg).call().content();

    }

    @GetMapping("/chatmodelv2/dochat")
    public String doChat2(@RequestParam(name = "msg", defaultValue = "你是谁?") String msg) {
        return chatModel.call(msg);
    }

}