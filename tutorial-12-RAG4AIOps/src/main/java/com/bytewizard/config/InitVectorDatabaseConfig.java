package com.bytewizard.config;

import cn.hutool.crypto.SecureUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class InitVectorDatabaseConfig {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("classpath:document/ops.txt")
    private Resource opsFile;

    @PostConstruct
    public void init() {
        // 1 读取文件
        TextReader textReader = new TextReader(opsFile);
        textReader.setCharset(Charset.defaultCharset());

        // 2 文件转化为向量(开启分词)
        List<Document> list = new TokenTextSplitter().transform(textReader.read());

        // 3 写入向量数据库redis stack
        // vectorStore.add(list);

        // 解决第三部向量数据重复问题, 使用redis setnx命令
        // 3 去重版
        String metaData = (String) textReader.getCustomMetadata().get("source");
        String textHash = SecureUtil.md5(metaData);
        String redisKey = "vector-xxx" + textHash;

        // 判断是否存入过
        Boolean retFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, "1");

        if (Boolean.TRUE.equals(retFlag)) {
            // 健不存在, 首次插入
            vectorStore.add(list);
        } else {
            System.out.println("向量初始化数据已经加载");
        }

    }

}
