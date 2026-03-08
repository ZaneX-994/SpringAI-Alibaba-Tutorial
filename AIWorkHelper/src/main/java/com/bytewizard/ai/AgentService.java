package com.bytewizard.ai;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Agent核心服务
 * 使用Spring AI的ChatClient + Function Calling实现Agent循环机制
 * 集成SummaryBufferChatMemory实现会话摘要缓冲记忆
 *
 * 工作流程:
 * 1. 用户输入 -> ChatClient接收
 * 2. MessageChatMemoryAdvisor自动加载历史对话（包含摘要）
 * 3. LLM分析用户意图，决定调用哪些Tool
 * 4. Tool执行并返回结果
 * 5. LLM继续推理，可能调用更多Tool
 * 6. 生成最终答案返回用户
 * 7. 对话自动保存到Memory，Token超限时生成摘要
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgentService {

    private final ChatClient chatClient;


}
