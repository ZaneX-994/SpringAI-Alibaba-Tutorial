package com.bytewizard.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
public class ChatMemory4RedisController {

    @Qualifier("qwenChatClient")
    @Autowired
    private ChatClient qwenChatClient;


    /*
    * 测试用例:
    * 1. http://localhost:8008/chatmemory/chat?msg="我是Java"&userId=1
    * 2. http://localhost:8008/chatmemory/chat?msg="我是谁?"&userId=1
    * */
    @GetMapping("/chatmemory/chat")
    public String chat(@RequestParam(name = "msg") String msg, @RequestParam(name = "userId") String userId) {

        return qwenChatClient.prompt(msg).advisors(new Consumer<ChatClient.AdvisorSpec>() {
            @Override
            public void accept(ChatClient.AdvisorSpec advisorSpec) {
                advisorSpec.param(CONVERSATION_ID, userId);
            }
        }).call().content();
    }

}
