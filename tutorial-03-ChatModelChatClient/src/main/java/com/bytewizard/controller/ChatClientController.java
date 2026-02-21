package com.bytewizard.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatClientController {

    /*
    * chatClient依赖chatModel对象接口
    * */
    private final ChatClient chatClient;


    public ChatClientController(ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @GetMapping("/chatclient/dochat")
    public String chatModel(@RequestParam(name = "msg", defaultValue = "你是谁?") String msg) {

        return chatClient.prompt().user(msg).call().content();

    }
}
