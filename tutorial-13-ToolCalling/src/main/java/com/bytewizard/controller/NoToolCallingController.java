package com.bytewizard.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class NoToolCallingController {

    @Autowired
    private ChatModel chatModel;

    @GetMapping("/notoolcall/chat")
    public Flux<String> chat(@RequestParam(name = "msg", defaultValue = "现在几点了?") String msg) {
        return chatModel.stream(msg);
    }

}
