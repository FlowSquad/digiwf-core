package io.muenchendigital.digiwf.message.adapter.springcloudstream;

import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.error.TechnicalError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
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
            Optional<Object> processInstance = Optional.empty();
            Optional<Object> messageName = Optional.empty();

            if (error.getPayload() instanceof MessagingException) {
                final MessagingException messageHandlingException = (MessagingException) error.getPayload();
                final Message<?> failedMessage = messageHandlingException.getFailedMessage();
                processInstance = Optional.ofNullable(failedMessage.getHeaders().get(StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID));
                messageName = Optional.ofNullable(failedMessage.getHeaders().get(StreamingHeaders.DIGIWF_MESSAGE_NAME));
            }

            // technical error
            if (error.getPayload().getCause() instanceof TechnicalError) {
                final TechnicalError technicalError = (TechnicalError) error.getPayload().getCause();
                this.processApi.handleTechnicalError(technicalError.getProcessInstanceId(), technicalError.getErrorCode(), technicalError.getErrorMessage());
                log.info("Handling technical error for process {} error {}", technicalError.getProcessInstanceId(), technicalError.getErrorMessage());
                return;
            }

            // otherwise incident
            if (processInstance.isPresent() && messageName.isPresent()) {
                final String processInstanceId = processInstance.get().toString();
                final String digiwfMessageName = messageName.get().toString();
                final String errorMsg = error.getPayload().getMessage();
                this.processApi.handleIncident(processInstanceId, digiwfMessageName, errorMsg);
                log.info("Creating incident for process {} error {}", processInstanceId, errorMsg);
                return;
            }

            log.error("Exception was not handled. Exception was {}", error.getPayload().getCause().getMessage());
        };
    }

}
