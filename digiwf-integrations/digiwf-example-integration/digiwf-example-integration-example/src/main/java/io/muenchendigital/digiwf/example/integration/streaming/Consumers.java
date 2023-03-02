package io.muenchendigital.digiwf.example.integration.streaming;

import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.TechnicalErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * This class contains consumers to debug the integration.
 */
@Slf4j
@Component
public class Consumers {

    @Bean
    public Consumer<Message<TechnicalErrorDto>> onTechnicalErrorConsumer() {
        return message -> {
            log.warn("Technical Error: {}", message.toString());
        };
    }

    @Bean
    public Consumer<Message<Object>> onIncidentConsumer() {
        return message -> {
            log.warn("Incident:  {}", message.toString());
        };
    }

    @Bean
    public Consumer<Message<CorrelateMessageDto>> correlateMessageConsumer() {
        return message -> {
            log.info(message.getPayload().toString());
        };
    }

}
