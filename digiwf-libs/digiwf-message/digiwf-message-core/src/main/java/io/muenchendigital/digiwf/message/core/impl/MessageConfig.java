package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.common.Api;
import io.muenchendigital.digiwf.message.core.api.IncidentApi;
import io.muenchendigital.digiwf.message.core.api.SendMessageApi;
import io.muenchendigital.digiwf.message.core.api.TechnicalErrorApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MessageConfig {

    private final MessageService messageService;

    @Api
    @ConditionalOnMissingBean
    public SendMessageApi sendMessageApi() {
        return this.messageService;
    }

    @Api
    @ConditionalOnMissingBean
    public TechnicalErrorApi technicalErrorApi() {
        return this.messageService;
    }

    @Api
    @ConditionalOnMissingBean
    public IncidentApi incidentApi() {
        return this.messageService;
    }
}
