package com.bytewizard.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class PromptTemplateController {

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


    /**
     * @Description: PromptTemplate基本使用, 使用占位符设置模版
     * 测试地址: http://localhost:8006/prompttemplate/chat?topic=java&output_format=html&wordCount=200
     */
    @GetMapping("/prompttemplate/chat")
    public Flux<String> chat(@RequestParam(name = "topic") String topic, @RequestParam(name = "output_format") String output_format, @RequestParam(name = "wordCount") String wordCount) {
        PromptTemplate promptTemplate = new PromptTemplate("" +
                "讲一个关于{topic}的故事" +
                "并以{output_format}格式输出, " +
                "字数在{wordCount}左右");

        // PromptTemplate -> Prompt
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "output_format", output_format,
                "wordCount", wordCount
        ));

        return deepseekChatClient.prompt(prompt).stream().content();
    }

    @Value("classpath:/prompttemplate/bytewizard-template.txt")
    private Resource userTemplate;
    /**
     * Description: PromptTemplate 读取模版文件实现模版功能
     * 测试地址: http://localhost:8006/prompttemplate/chat2?topic=java&output_format=html
     */
    @GetMapping("/prompttemplate/chat2")
    public Flux<String> chat2(@RequestParam(name = "topic") String topic, @RequestParam(name = "output_format") String output_format) {
        PromptTemplate promptTemplate = new PromptTemplate(userTemplate);
        Prompt prompt = promptTemplate.create(Map.of("topic", topic, "output_format", output_format));
        return qwenChatClient.prompt(prompt).stream().content();
    }

    /**
     * Description: PromptTemplate 多角色设定
     * 系统消息(SystemMessage): 设定AI的行为规则和功能边界(xxx助手/什么格式返回/字数控制)
     * 用户消息(UserMessage): 用户的提问/主题
     * 测试地址: http://localhost:8006/prompttemplate/chat3?sysTopic=法律&userTopic=知识产权
     */
    @GetMapping("/prompttemplate/chat3")
    public Flux<String> chat3(@RequestParam(name = "sysTopic") String sysTopic, @RequestParam(name = "userTopic") String userTopic) {

        // 1. SystemPromptTemplate
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(
                "你是{systemTopic}助手, 只回答{systemTopic}问题, 其它无可奉告, 以html格式输出"
        );
        Message sysMessage = systemPromptTemplate.createMessage(Map.of("systemTopic", sysTopic));

        // 2. PromptTemplate
        PromptTemplate promptTemplate = new PromptTemplate("解释一下{userTopic}");
        Message userMessage = promptTemplate.createMessage(Map.of("userTopic", userTopic));

        // 3. 组合多个Message -> Prompt
        Prompt prompt = new Prompt(List.of(sysMessage, userMessage));

        // 4. 调用LLM
        return deepseekChatClient.prompt(prompt).stream().content();

    }

    /*
    * Description: 人物角色设定, 通过SystemMessage来实现人物设定, 本案例用ChatModel实现
    * */
    @GetMapping("/prompttemplate/chat4")
    public String chat4(@RequestParam(name = "question") String question) {
        // 1. 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个Java编程助手, 拒绝回答非技术问题");

        // 2. 用户消息
        UserMessage userMessage = new UserMessage(question);

        // 3. 系统 + 用户 -> 完整提示词
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        // 4. 调用LLM
        return qwenChatModel.call(prompt).getResult().getOutput().getText();
    }

    /**
     * Description: 人物角色设定, 通过SystemMessage来实现人物设定, 本案例用ChatClient实现
     * 设定AI为"医疗专家"时, 仅回答医学相关问题
     * 设定AI为"编程助手"时, 专注于技术问题解答
     * 测试地址: http://localhost:8006/prompttemplate/chat5?question=火锅
     */
    @GetMapping("/prompttemplate/chat5")
    public Flux<String> chat5(@RequestParam(name = "question") String question) {
        return deepseekChatClient.prompt()
                .system("你是一个Java编程助手, 拒绝回答非技术问题")
                .user(question)
                .stream()
                .content();
    }
}
