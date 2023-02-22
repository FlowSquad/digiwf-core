package example.shared.streaming;

import example.process.dto.ProcessMessageDto;
import example.process.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.error.TechnicalError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class Consumers {

    @Bean
    public Consumer<Message<Object>> onTechnicalError() {
        return message -> log.warn("Technical Error: {}", message.toString());
    }

    @Bean
    public Consumer<Message<Object>> onIncident() {
        return message -> log.warn("Incident:  {}", message.toString());
    }

    @Bean
    @MessageExceptionHandler
    public Consumer<Message<StartProcessDto>> startProcess() {
        return message -> log.info(message.toString());
    }

    @Bean
    @MessageExceptionHandler
    public Consumer<Message<ProcessMessageDto>> correlateMessage() {
        return message -> {
            if (message.getPayload().getPayloadVariables().isEmpty()) {
                // incident
                // throw new RuntimeException("Can not correlate message. BusinessKey is missing.");

                // technical error
                throw new TechnicalError(message.getPayload().getProcessInstanceId(), "400", "No variables defined.");
            }
            log.info(message.getPayload().toString());
        };
    }

}
