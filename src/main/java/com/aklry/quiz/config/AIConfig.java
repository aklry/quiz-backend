package com.aklry.quiz.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class AIConfig {
    private String apiKey;

    @Bean
    public GenerationParam getGenerationParam() {
        return GenerationParam.builder()
                .apiKey(this.apiKey)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .model(Generation.Models.QWEN_PLUS)
                .build();
    }
}
