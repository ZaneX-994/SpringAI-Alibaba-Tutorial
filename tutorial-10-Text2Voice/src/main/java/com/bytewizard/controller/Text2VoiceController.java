package com.bytewizard.controller;

import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@RestController
public class Text2VoiceController {

    @Autowired
    private SpeechSynthesisModel synthesisModel;

    // voice model
    public static final String BAILIAN_VOICE_MODEL = "cosyvoice-v3-flash";

    // voice timber
    public static final String BAILIAN_VOICE_TIMBER = "longanyang";

    @GetMapping("/t2v/voice")
    public String voice(@RequestParam(name = "msg", defaultValue = "支付宝到账100元") String msg) {
        String filePath = "/Users/zihang/java/IdeaProjects/SpringAIAlibaba-Tutorial/tutorial-10-Text2Voice/src/main/resources/voice/"
                + UUID.randomUUID() + ".mp3";

        // 1. 语音参数设置

        DashScopeSpeechSynthesisOptions options = DashScopeSpeechSynthesisOptions.builder()
                .model(BAILIAN_VOICE_MODEL)
                .voice(BAILIAN_VOICE_TIMBER)
                .build();

        // 2. 调用大模型语音生成对象
        SpeechSynthesisResponse response = synthesisModel.call(new SpeechSynthesisPrompt(msg, options));

        // 3. 字节流语音转换
        ByteBuffer byteBuffer = response.getResult().getOutput().getAudio();

        // 4. 文件生成
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(byteBuffer.array());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 5. 生成路径
        return filePath;
    }


}
