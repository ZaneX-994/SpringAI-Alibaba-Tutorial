package com.bytewizard.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatHelloController {

    @Autowired
    private ChatModel chatModel;

    /*
    * 普通通用调用
    * */
    @GetMapping("/hello/dochat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        String result = chatModel.call(msg);
        return result;
    }

    /*
    * 流式返回调用
    * */
    @GetMapping("/hello/streamchat")
    public Flux<String> stream(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatModel.stream(msg);
    }


}
