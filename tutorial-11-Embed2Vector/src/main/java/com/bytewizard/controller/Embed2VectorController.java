package com.bytewizard.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class Embed2VectorController {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private VectorStore vectorStore;

    /*
    * 文本向量化:
    * http://localhost:8011/text2embed?msg="你好时世界"
    * */
    @GetMapping("/text2embed")
    public EmbeddingResponse text2Embed(@RequestParam(name = "msg") String msg) {
        // 使用配置文件
         EmbeddingResponse response = embeddingModel.call(new EmbeddingRequest(List.of(msg), null));
        // 指定向量模型
        //  EmbeddingResponse response = embeddingModel.call(new EmbeddingRequest(List.of(msg), DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()));
        System.out.println(Arrays.toString(response.getResult().getOutput()));

        return response;
    }

    /*
    * 文本向量化后存入redis
    * */
    @GetMapping("/embed2vector/add")
    public void add() {
        List<Document> documents = List.of(
                new Document("i study LLM"),
                new Document("i love java")
        );

        vectorStore.add(documents);
    }


    /*
    * 从向量数据库RedisStack查找, 进行相似度查找
    * http://localhost:8011/embed2vector/get?msg=LLM
    * */
    @GetMapping("/embed2vector/get")
    public List getAll(@RequestParam(name = "msg") String msg) {
        SearchRequest request = SearchRequest.builder()
                .query(msg)
                .topK(2)
                .build();

        List<Document> documents = vectorStore.similaritySearch(request);
        return documents;
    }

}
