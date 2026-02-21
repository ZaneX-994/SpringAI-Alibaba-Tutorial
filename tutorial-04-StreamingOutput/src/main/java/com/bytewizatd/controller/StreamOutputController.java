package com.bytewizatd.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamOutputController {

    @Qualifier("deepseek")
    @Autowired()
    private ChatModel deepseekChatModel;

    @Qualifier("qwen")
    @Autowired()
    private ChatModel qwenChatModel;

    @Qualifier("deepseekChatClient")
    @Autowired
    private ChatClient deeseekChatClient;

    @Qualifier("qwenChatClient")
    @Autowired
    private ChatClient qwenChatClient;



    @GetMapping("/stream/chatflux1")
    public Flux<String> chatFlux(@RequestParam(name = "question", defaultValue = "你是谁?") String question) {
        return deepseekChatModel.stream(question);
    }

    @GetMapping("/stream/chatflux2")
    public Flux<String> chatFlux2(@RequestParam(name = "question", defaultValue = "你是谁?") String question) {
        return qwenChatModel.stream(question);
    }

    @GetMapping("/stream/chatflux3")
    public Flux<String> chatFlux3(@RequestParam(name = "question", defaultValue = "你是谁?") String question) {
        return deeseekChatClient.prompt(question).stream().content();
    }

    @GetMapping("/stream/chatflux4")
    public Flux<String> chatFlux4(@RequestParam(name = "question", defaultValue = "你是谁?") String question) {
        return qwenChatClient.prompt(question).stream().content();
    }



}
