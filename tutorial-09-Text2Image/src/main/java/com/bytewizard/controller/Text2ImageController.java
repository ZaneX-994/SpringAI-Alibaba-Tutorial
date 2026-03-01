package com.bytewizard.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Text2ImageController {

    // img model
    public static final String IMG_MODEL = "wanx2.1-t2i-turbo";

    @Autowired
    private ImageModel imageModel;

    @GetMapping("/t2i/image")
    public String image(@RequestParam(name = "prompt", defaultValue = "刺猬") String prompt) {
        return imageModel.call(
                new ImagePrompt(prompt, DashScopeImageOptions.builder().withModel(IMG_MODEL).build())
                )
                .getResult()
                .getOutput()
                .getUrl();
    }


}
