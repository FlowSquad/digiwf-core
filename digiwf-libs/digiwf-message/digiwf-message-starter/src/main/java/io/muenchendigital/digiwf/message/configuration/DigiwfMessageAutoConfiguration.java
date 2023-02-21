package io.muenchendigital.digiwf.message.configuration;


import io.muenchendigital.digiwf.message.adapter.springcloudstream.SpringCloudStreamAdapter;
import io.muenchendigital.digiwf.message.adapter.springcloudstream.infrastructure.RoutingCallback;
import io.muenchendigital.digiwf.message.core.impl.MessageService;
import io.muenchendigital.digiwf.message.core.impl.SendMessagePort;
import io.muenchendigital.digiwf.message.process.impl.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.impl.ProcessService;
import io.muenchendigital.digiwf.message.process.impl.StartProcessPort;
import io.muenchendigital.digiwf.message.properties.DigiwfMessageProperties;
import io.muenchendigital.digiwf.message.properties.DigiwfStreamingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.message")
@EnableConfigurationProperties(value = { DigiwfMessageProperties.class, DigiwfStreamingProperties.class })
public class DigiwfMessageAutoConfiguration {

    private final DigiwfMessageProperties digiwfMessageProperties;
    private final DigiwfStreamingProperties digiwfStreamingProperties;

    @Bean
    public MessageService messageService(final SendMessagePort messagePort) {
        return new MessageService(
                messagePort,
                this.digiwfMessageProperties.getIncidentDestination(),
                this.digiwfMessageProperties.getTechnicalErrorDestination()
        );
    }

    @Bean
    public ProcessService processService(final CorrelateMessagePort correlateMessagePort, final StartProcessPort startProcessPort) {
        return new ProcessService(
                this.digiwfMessageProperties.getCorrelateMessageDestination(),
                this.digiwfMessageProperties.getStartProcessDestination(),
                correlateMessagePort,
                startProcessPort
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringCloudStreamAdapter springCloudStreamAdapter(final Sinks.Many<Message<Object>> messageSink) {
        return new SpringCloudStreamAdapter(messageSink);
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback customRouter() {
        return new RoutingCallback(this.digiwfStreamingProperties.getTypeMappings());
    }

}
