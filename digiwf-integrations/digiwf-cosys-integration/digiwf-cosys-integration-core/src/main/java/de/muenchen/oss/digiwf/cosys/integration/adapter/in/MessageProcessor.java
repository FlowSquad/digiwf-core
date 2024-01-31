package de.muenchen.oss.digiwf.cosys.integration.adapter.in;


import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocument;
import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateWebformUrlInPort;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import jakarta.validation.ValidationException;

import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@RequiredArgsConstructor
public class MessageProcessor {

    private final CreateDocument documentUseCase;
    private final CreateWebformUrlInPort createWebformUrlInPort;

    private final ErrorApi errorApi;
    private final ProcessApi processApi;

    /**
     * All messages from the route "generateDocument" go here.
     *
     * @return the consumer
     */
    public Consumer<Message<GenerateDocument>> cosysIntegration() {
        return message -> {
            try {
            log.info("Processing generate document request from eventbus");
            final GenerateDocument document = message.getPayload();
            log.debug("Generate document request: {}", document);
                this.documentUseCase.createDocument(
                        message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME, String.class),
                        document);
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final ValidationException validationException) {
                this.errorApi.handleBpmnError(message.getHeaders(), new BpmnError("VALIDATION_ERROR", validationException.getMessage()));
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public Consumer<Message<CreateWebformUrlDto>> createWebformId() {
        return message -> {
            try {
                final CreateWebformUrlDto dto = message.getPayload();
                log.debug("Generate webform id request: {}", dto);
                final String url = this.createWebformUrlInPort.createWebformUrl(dto.getGuid(), dto.getRole(), dto.getClient());
                processApi.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class),  message.getHeaders().get(DIGIWF_MESSAGE_NAME, String.class), Map.of("url", url));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final ValidationException validationException) {
                this.errorApi.handleBpmnError(message.getHeaders(), new BpmnError("VALIDATION_ERROR", validationException.getMessage()));
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

}
