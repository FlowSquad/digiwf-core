package de.muenchen.oss.digiwf.signing.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.signing.integration.application.port.in.CreateSigningInPort;
import de.muenchen.oss.digiwf.signing.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.signing.integration.domain.model.CreateSigning;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MessageProcessor {

    private final IntegrationOutPort integrationOutPort;
    private final CreateSigningInPort createSigningInPort;

    private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";

    /**
     * All messages from the route "createSigning" go here.
     *
     * @return the consumer
     */
    @Bean
    public Consumer<Message<CreateSigningEvent>> createSigning() {
        return message -> {
            log.info("Processing new request from eventbus");
            final CreateSigningEvent request = message.getPayload();
            val headers = message.getHeaders();
            log.debug("Request: {}", request);
            try {
                final CreateSigning response = createSigningInPort.createSigning(request.getDocumentPath());
                Map<String, Object> result = Map.of("storageUrl", response.getStorageUrl(), "signingUrl", response.getSigningUrl());
                integrationOutPort.correlateProcessMessage(headers, result);
            } catch (ConstraintViolationException cve) {
                integrationOutPort.handleBpmnError(headers, new BpmnError(VALIDATION_ERROR_CODE, cve.getMessage()));
            } catch (final Exception e) {
                log.error("Request could not be fulfilled", e);
                integrationOutPort.handleIncident(headers, new IncidentError(e.getMessage()));
            }
        };
    }

}
