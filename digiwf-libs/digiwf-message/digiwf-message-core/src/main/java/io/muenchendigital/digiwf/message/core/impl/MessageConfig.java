package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.core.api.IncidentApi;
import io.muenchendigital.digiwf.message.core.api.SendMessageApi;
import io.muenchendigital.digiwf.message.core.api.TechnicalErrorApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MessageConfig {

    private final MessageService messageService;

    @Bean
    @ConditionalOnMissingBean
    public SendMessageApi sendMessageApi() {
        return this.messageService;
    }

    @Bean
    @ConditionalOnMissingBean
    public TechnicalErrorApi technicalErrorApi() {
        return this.messageService;
    }

    @Bean
    @ConditionalOnMissingBean
    public IncidentApi incidentApi() {
        return this.messageService;
    }
}
