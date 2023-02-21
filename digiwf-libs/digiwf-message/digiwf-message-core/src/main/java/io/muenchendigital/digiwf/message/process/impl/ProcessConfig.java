package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.process.api.CorrelateMessageApi;
import io.muenchendigital.digiwf.message.process.api.StartProcessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProcessConfig {
    private final ProcessService processService;

    @Bean
    @ConditionalOnMissingBean
    public CorrelateMessageApi correlateMessageApi() {
        return this.processService;
    }

    @Bean
    @ConditionalOnMissingBean
    public StartProcessApi startProcessApi() {
        return this.processService;
    }
}
