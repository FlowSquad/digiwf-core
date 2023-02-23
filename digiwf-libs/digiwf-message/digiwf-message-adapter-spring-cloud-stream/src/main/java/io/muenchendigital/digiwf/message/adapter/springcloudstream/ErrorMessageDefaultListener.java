package io.muenchendigital.digiwf.message.adapter.springcloudstream;

import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.error.TechnicalError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorMessageDefaultListener {

    private final ProcessApi processApi;

    public static final String FUNCTION_ROUTING_ERROR = "springCloudstreamUtilsFunctionRoutingError";
    public static final String MISSING_TYPE_HEADER_ERROR = "springCloudstreamUtilsMissingTypeHeaderError";

    /**
     * When TYPE header of the incoming message is set but not configured in the application, message gets routed here.
     * @return Consumer
     */
    @Bean
    public Consumer<Message<Object>> springCloudstreamUtilsFunctionRoutingError() {
        return message -> log.error("Message handling for messages with type '{}' is not implemented. (message {})", message.getHeaders().get(StreamingHeaders.TYPE), message.getHeaders().get(MessageHeaders.ID));
    }

    /**
     * When TYPE header of the incoming message is unset, message gets routed here.
     * @return Consumer
     */
    @Bean
    public Consumer<Message<Object>> springCloudstreamUtilsMissingTypeHeaderError() {
        return message -> log.error("The message header '{}' must be set in message '{}'.", StreamingHeaders.TYPE, message.getHeaders().get(MessageHeaders.ID));
    }

    @Bean
    public Consumer<ErrorMessage> customErrorHandler() {
        return error -> {
            if (error.getPayload().getCause() instanceof TechnicalError) {
                final TechnicalError technicalError = (TechnicalError) error.getPayload().getCause();
                this.processApi.handleTechnicalError(technicalError.getProcessInstanceId(), technicalError.getErrorCode(), technicalError.getErrorMessage());
                log.info("Handling technical error for process {} error {}", technicalError.getProcessInstanceId(), technicalError.getErrorMessage());
                return;
            }
            // incident
            // Todo original message is always null
            if (error.getOriginalMessage() != null) {
                final Message<?> originalMessage = error.getOriginalMessage();
                final Optional<Object> processInstance = Optional.ofNullable(originalMessage.getHeaders().get(StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID));
                final Optional<Object> messageName = Optional.ofNullable(originalMessage.getHeaders().get(StreamingHeaders.DIGIWF_MESSAGE_NAME));
                this.processApi.handleIncident(
                        processInstance.orElse("").toString(),
                        messageName.orElse("").toString(),
                        error.getPayload().getMessage());
                log.info("Creating incident for process {} error {}", processInstance.orElse("").toString(), messageName.orElse("").toString());
                return;
            }
            log.warn("Exception was not handled. Exception was {}", error.getPayload().getCause().getMessage());
        };
    }

}
