package com.bytewizard.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class PromptController {

    @Qualifier("deepseek")
    @Autowired
    private ChatModel deepseekChatModel;

    @Qualifier("qwen")
    @Autowired
    private ChatModel qwenChatModel;

    @Qualifier("deepseekChatClient")
    @Autowired
    private ChatClient deepseekChatClient;

    @Qualifier("qwenChatClient")
    @Autowired
    private ChatClient qwenChatClient;


    // 测试: http://localhost:8005/prompt/chat?question=火锅介绍下
    @GetMapping("/prompt/chat")
    public Flux<String> chat(@RequestParam(name = "question") String question) {
        return deepseekChatClient.prompt()
                // AI 能力边界
                .system("你是一个法律助手, 只能回答法律问题, 其它问题回复: 我只能回答法律相关问题, 其它无可奉告")
                .user(question)
                .stream()
                .content();
    }

    // 测试: http://localhost:8005/prompt/chat2?question=葫芦娃
    // Flux<ChatResponse>.content() -> Flux<String>
    @GetMapping("/prompt/chat2")
    public Flux<ChatResponse> chat2(@RequestParam(name = "question") String question) {

        // 系统消息: 确认大模型边界
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手, 每个故事控制在300字以内");

        // 用户消息
        UserMessage userMessage = new UserMessage(question);

        // prompt
        Prompt prompt = new Prompt(systemMessage, userMessage);

        return deepseekChatModel.stream(prompt);
    }

    @GetMapping("/prompt/chat3")
    public Flux<String> chat3(@RequestParam(name = "question") String question) {

        // 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手, 每个故事控制在600字以内且以HTML格式返回");

        // 用户消息
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(systemMessage, userMessage);

        return deepseekChatModel.stream(prompt)
                .map(response -> response.getResults().get(0).getOutput().getText());
    }

    // AssistantMessage: AI返回的响应信息
    @GetMapping("/prompt/chat4")
    public String chat4(@RequestParam(name = "question") String question) {

        AssistantMessage assistantMessage = deepseekChatClient.prompt()
                .user(question)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();

        return assistantMessage.getText();
    }

    @GetMapping("/prompt/chat5")
    public String chat5(@RequestParam(name = "city") String city) {
        String answer = deepseekChatClient.prompt()
                .user(city + "未来三天天气如何?")
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();

        // 测试用例, 并非真正的工具
        ToolResponseMessage toolResponseMessage = new ToolResponseMessage(
                List.of(new ToolResponseMessage.ToolResponse("1", "获得天气", city))
        );

        String toolResponse = toolResponseMessage.getText();

        String result = answer + toolResponse;

        return result;
    }

}
