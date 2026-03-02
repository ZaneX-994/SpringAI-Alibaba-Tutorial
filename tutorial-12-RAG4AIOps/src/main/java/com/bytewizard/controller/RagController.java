package com.bytewizard.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class RagController {

    @Qualifier("qwenChatClient")
    @Autowired
    private ChatClient chatClient;

    @Autowired
    private VectorStore vectorStore;

    @GetMapping("/rag4aiops")
    public Flux<String> rag(@RequestParam(name = "msg") String msg) {
        String systemInfo = """
                你是一个运维工程师, 按照给出的编码给出对应的故障解释, 否则回复找不到消息
                """;

        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build())
                .build();

        return chatClient.prompt().system(systemInfo).user(msg).advisors(advisor).stream().content();

    }

}
