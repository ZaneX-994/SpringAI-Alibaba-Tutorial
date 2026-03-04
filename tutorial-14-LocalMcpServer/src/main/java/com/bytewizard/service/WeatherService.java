package com.bytewizard.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeatherService {

    @Tool(description = "根据城市名称获取天气预报")
    public String getWeatherByCity(String city) {
        Map<String, String> map = Map.of(
                "北京", "11111降雨频繁, 其中今天和后天雨势较强, 部分天气有暴雨并伴强对流天气, 需注意",
                "上海", "22222多云, 15 ~ 27摄氏度, 南风三级, 当前温度27摄氏度",
                "深圳", "33333多云40天, 阴16天, 雨30天, 晴3天"
        );
        return map.getOrDefault(city, "抱歉, 未查询到对应城市!");
    }

}
