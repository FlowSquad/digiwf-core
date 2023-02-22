package io.muenchendigital.digiwf.message.configuration;

import io.muenchendigital.digiwf.message.adapter.springcloudstream.SpringCloudStreamAdapter;
import io.muenchendigital.digiwf.message.core.api.IncidentApi;
import io.muenchendigital.digiwf.message.core.api.SendMessageApi;
import io.muenchendigital.digiwf.message.core.api.TechnicalErrorApi;
import io.muenchendigital.digiwf.message.core.impl.DigiwfMessageService;
import io.muenchendigital.digiwf.message.core.impl.SendMessagePort;
import io.muenchendigital.digiwf.message.process.api.CorrelateMessageApi;
import io.muenchendigital.digiwf.message.process.api.StartProcessApi;
import io.muenchendigital.digiwf.message.process.impl.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.impl.DigiwfProcessService;
import io.muenchendigital.digiwf.message.process.impl.StartProcessPort;
import io.muenchendigital.digiwf.message.properties.DigiwfMessageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.message")
@EnableConfigurationProperties(value = DigiwfMessageProperties.class)
public class DigiwfMessageAutoConfiguration {

    private final DigiwfMessageProperties digiwfMessageProperties;
    private final Sinks.Many<Message<Object>> messageSink;

    @Bean
    @ConditionalOnMissingBean
    public DigiwfMessageService digiwfMessageService(final SendMessagePort messagePort) {
        return new DigiwfMessageService(
                messagePort,
                this.digiwfMessageProperties.getIncidentDestination(),
                this.digiwfMessageProperties.getTechnicalErrorDestination()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public DigiwfProcessService digiwfProcessService(final CorrelateMessagePort correlateMessagePort, final StartProcessPort startProcessPort) {
        return new DigiwfProcessService(
                this.digiwfMessageProperties.getCorrelateMessageDestination(),
                this.digiwfMessageProperties.getStartProcessDestination(),
                correlateMessagePort,
                startProcessPort
        );
    }

    // process api

    @Bean
    @ConditionalOnMissingBean
    public CorrelateMessageApi correlateMessageApi(final DigiwfProcessService processService) {
        return processService;
    }

    @Bean
    @ConditionalOnMissingBean
    public StartProcessApi startProcessApi(final DigiwfProcessService processService) {
        return processService;
    }

    // message api

    @Bean
    @ConditionalOnMissingBean
    public SendMessageApi sendMessageApi(final DigiwfMessageService messageService) {
        return messageService;
    }

    @Bean
    @ConditionalOnMissingBean
    public TechnicalErrorApi technicalErrorApi(final DigiwfMessageService messageService) {
        return messageService;
    }

    @Bean
    @ConditionalOnMissingBean
    public IncidentApi incidentApi(final DigiwfMessageService messageService) {
        return messageService;
    }

    // process port

    @Bean
    @ConditionalOnMissingBean
    public CorrelateMessagePort correlatMessagePort() {
        return this.springCloudStreamAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public StartProcessPort startProcessPort() {
        return this.springCloudStreamAdapter();
    }

    // message ports

    @Bean
    @ConditionalOnMissingBean
    public SendMessagePort sendMessagePort() {
        return this.springCloudStreamAdapter();
    }

//    @Bean
//    @ConditionalOnMissingBean
    public SpringCloudStreamAdapter springCloudStreamAdapter() {
        return new SpringCloudStreamAdapter(this.messageSink);
    }

}
