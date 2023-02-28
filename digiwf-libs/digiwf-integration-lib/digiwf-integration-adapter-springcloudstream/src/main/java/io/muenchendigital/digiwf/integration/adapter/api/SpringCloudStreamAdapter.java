package io.muenchendigital.digiwf.integration.adapter.api;

import io.muenchendigital.digiwf.integration.adapter.shared.StreamingHeaders;
import io.muenchendigital.digiwf.integration.core.api.IntegrationExecuteApi;
import io.muenchendigital.digiwf.integration.core.api.TechnicalError;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpringCloudStreamAdapter {
    private final IntegrationExecuteApi integrationExecuteApi;
    private final ProcessApi processApi;

    @Bean
    public Consumer<Message<Map<String,Object>>> receiveMessages() {
        return message -> {
            // get process infos from headers
            final Optional<Object> processInstanceHeader = Optional.ofNullable(message.getHeaders().get(StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID));
            final Optional<Object> messageNameHeader = Optional.ofNullable(message.getHeaders().get(StreamingHeaders.DIGIWF_MESSAGE_NAME));
            final Optional<Object> typeHeader = Optional.ofNullable(message.getHeaders().get(StreamingHeaders.TYPE));

            try {
                final String processInstance = processInstanceHeader.orElseThrow(() -> new RuntimeException("Process instance id is missing in message headers. Message will be ignored.")).toString();
                final String messageName = messageNameHeader.orElseThrow(() -> new RuntimeException("Message type is missing in message headers. Message will be ignored.")).toString();
                final String messageType = typeHeader.orElseThrow(() -> new RuntimeException("Message type is missing in message headers. Message will be ignored.")).toString();

                // add process instance, messageName and messageType to the payload
                final Map<String, Object> payload = message.getPayload();
                payload.put(StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID, processInstance);
                payload.put(StreamingHeaders.DIGIWF_MESSAGE_NAME, messageName);
                payload.put(StreamingHeaders.TYPE, messageType);

                // pass payload to the integration
                final Map<String, Object> result = (Map<String, Object>) this.integrationExecuteApi.execute(messageType, payload);

                // correlate message
                this.processApi.correlateMessage(processInstance, messageName, result);
            } catch (final TechnicalError technicalError) {
                this.processApi.handleTechnicalError(technicalError.getProcessInstanceId(), technicalError.getErrorCode(), technicalError.getErrorMessage());
                log.warn("Handling technical error for process {} error {}", technicalError.getProcessInstanceId(), technicalError.getErrorMessage());
            } catch (final Exception e) {
                if (processInstanceHeader.isPresent() && messageNameHeader.isPresent()) {
                    this.processApi.handleIncident(processInstanceHeader.get().toString(), messageNameHeader.get().toString(), e.getMessage());
                    log.error("Handling incident for process {} message {} error {}", processInstanceHeader.get(), messageNameHeader.get(), e.getMessage());
                } else {
                    log.error(e.getMessage());
                }
            }
        };
    }

}
