package com.bytewizard.service.impl;

import com.bytewizard.dto.websocket.ChatMessage;
import com.bytewizard.entity.ChatLog;
import com.bytewizard.repository.ChatLogRepository;
import com.bytewizard.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 聊天服务实现
 * 使用AgentService处理AI聊天请求（基于Spring AI Function Calling）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatLogRepository chatLogRepository;

    @Override
    public void savePrivateChat(ChatMessage message) {
        log.info("保存私聊消息: from={}, to={}", message.getSendId(), message.getRecvId());
        saveChatLog(message);
    }

    @Override
    public void saveGroupChat(ChatMessage message) {
        log.info("保存群聊消息: from={}, conversationId={}",
                message.getSendId(), message.getConversationId());
        saveChatLog(message);
    }

    @Override
    public String handleAIChat(String userId, String content, String relationId, Long startTime, Long endTime) {
        return "";
    }

    @Override
    public List<ChatMessage> getChatHistory(String conversationId, int limit) {
        log.info("获取聊天历史: conversationId={}, limit={}", conversationId, limit);

        if (conversationId == null || conversationId.isEmpty()) {
            return new ArrayList<>();
        }

        // 查询聊天记录，按发送时间倒序
        PageRequest pageRequest = PageRequest.of(0, limit,
                Sort.by(Sort.Direction.DESC, "sendTime"));

        List<ChatLog> chatLogs = chatLogRepository
                .findByConversationId(conversationId, pageRequest)
                .getContent();

        // 转换为WebSocketMessage
        return chatLogs.stream()
                .map(this::convertToWebSocketMessage)
                .collect(Collectors.toList());
    }

    /**
     * 保存聊天消息到数据库
     */
    private void saveChatLog(ChatMessage message) {
        long currentTime = System.currentTimeMillis() / 1000;

        ChatLog chatLog = new ChatLog();
        chatLog.setConversationId(message.getConversationId());
        chatLog.setSendId(message.getSendId());
        chatLog.setRecvId(message.getRecvId());
        chatLog.setChatType(message.getChatType());
        chatLog.setMsgContent(message.getContent());
        chatLog.setSendTime(currentTime);

        try {
            chatLogRepository.save(chatLog);
            log.debug("聊天消息已保存: id={}", chatLog.getId());
        } catch (Exception e) {
            log.error("保存聊天消息失败", e);
        }
    }

    /**
     * 将ChatLog转换为ChatMessage
     */
    private ChatMessage convertToWebSocketMessage(ChatLog chatLog) {
        return ChatMessage.builder()
                .conversationId(chatLog.getConversationId())
                .sendId(chatLog.getSendId())
                .recvId(chatLog.getRecvId())
                .chatType(chatLog.getChatType())
                .content(chatLog.getMsgContent())
                .contentType(1) // 默认为文字类型
                .build();
    }
}
