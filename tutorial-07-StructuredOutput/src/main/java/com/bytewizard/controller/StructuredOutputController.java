package com.bytewizard.controller;

import com.bytewizard.records.StudentRecord;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
public class StructuredOutputController {

    @Qualifier("qwenChatClient")
    @Autowired
    private ChatClient qwenChatClient;


    @GetMapping("/structuredoutput/chat")
    public StudentRecord chat(@RequestParam(name = "sname") String name, @RequestParam(name = "email") String email) {

        return qwenChatClient.prompt().user(new Consumer<ChatClient.PromptUserSpec>() {
            @Override
            public void accept(ChatClient.PromptUserSpec promptUserSpec) {
                promptUserSpec.text("学号1001, 我叫{sname}, 大学专业计算机科学, 邮箱:{email}")
                        .param("sname", name)
                        .param("email", email);
            }
        }).call().entity(StudentRecord.class);

    }

    @GetMapping("/structuredoutput/chat2")
    public StudentRecord chat2(@RequestParam(name = "sname") String name, @RequestParam(name = "email") String email) {
        String template = """
                学号1001, 我叫{sname}, 大学专业软件工程, 邮箱:{email}
                """;
        return qwenChatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text("学号1001, 我叫{sname}, 大学专业计算机科学, 邮箱:{email}")
                .param("sname", name)
                .param("email", email)).call().entity(StudentRecord.class);
    }


}
