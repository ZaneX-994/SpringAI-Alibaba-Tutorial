package com.bytewizard.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OllamaController {

    @Qualifier("ollamaChatModel")
    @Autowired()
    private ChatModel chatModel;

    @GetMapping("/ollama/chat")
    public String chat(@RequestParam("msg") String msg) {
        String result = chatModel.call(msg);
        System.out.println("result: " + result);
        return result;
    }

    @GetMapping("/ollama/streamchat")
    public Flux<String> streamchat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatModel.stream(msg);
    }
}
