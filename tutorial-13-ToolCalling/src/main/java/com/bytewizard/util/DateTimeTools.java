package com.bytewizard.util;


import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;

public class DateTimeTools {

    /** 1. 定义function call (tool call)
     *  2. returnDirect
     *      true: tool 直接返回不走大模型, 直接给客户
     *      false: 拿到tool返回的结果并返回给大模型, 最后由大模型回复
     * */
    @Tool(description = "获取当前时间", returnDirect = false)
    public String getCurrentTime() {
        return LocalDateTime.now().toString();
    }
}
