package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.dto.TechnicalErrorDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;
import io.muenchendigital.digiwf.message.process.impl.port.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.impl.port.IncidentPort;
import io.muenchendigital.digiwf.message.process.impl.port.StartProcessPort;
import io.muenchendigital.digiwf.message.process.impl.port.TechnicalErrorPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static io.muenchendigital.digiwf.message.common.MessageConstants.*;

@Slf4j
@RequiredArgsConstructor
public class ProcessApiImpl implements ProcessApi {

    private final String correlateMessageDestination;
    private final String startProcessDestination;
    private final String incidentDestination;
    private final String technicalErrorDestination;

    private final CorrelateMessagePort correlateMessagePort;
    private final StartProcessPort startProcessPort;
    private final IncidentPort incidentMessagePort;
    private final TechnicalErrorPort technicalErrorMessagePort;

    private static final String CORRELATEMESSAGEV_01 = "correlatemessagev01";
    private static final String STARTPROCESS_V01 = "startProcessV01";

    @Override
    public boolean startProcess(final String processKey, final Map<String, Object> variables) {
        return this.startProcess(processKey, variables, null);
    }

    @Override
    public boolean startProcess(final String processKey, final Map<String, Object> variables, final String fileContext) {
        final StartProcessDto payload = StartProcessDto.builder()
                .key(processKey)
                .fileContext(fileContext)
                .data(variables)
                .build();
        final Message<StartProcessDto> message = new Message<>();
        message.addPayload(payload);
        message.addHeader(TYPE, STARTPROCESS_V01);
        return this.startProcessPort.startProcess(message, this.startProcessDestination);
    }

    @Override
    public boolean correlateMessage(final String processInstanceId, final String messageName, final Map<String, Object> payloadVariables) {
        final CorrelateMessageDto payload = CorrelateMessageDto.builder()
                .processInstanceId(processInstanceId)
                .messageName(messageName)
                .payloadVariables(payloadVariables)
                .build();
        final Message<CorrelateMessageDto> message = new Message<>();
        message.addPayload(payload);
        message.addHeader(TYPE, CORRELATEMESSAGEV_01);
        message.addHeader(DIGIWF_PROCESS_INSTANCE_ID, processInstanceId);
        message.addHeader(DIGIWF_MESSAGE_NAME, messageName);
        return this.correlateMessagePort.sendCorrelateMessage(message, this.correlateMessageDestination);
    }

    @Override
    public boolean handleIncident(final String processInstanceId, final String messageName, final String errorMessage) {
        log.error("Incident occured for process {} with error message {}", processInstanceId, errorMessage);
        final Message<Object> message = new Message<>();
        message.addHeader(TYPE, this.incidentDestination);
        message.addHeader(DIGIWF_PROCESS_INSTANCE_ID, processInstanceId);
        message.addHeader(DIGIWF_MESSAGE_NAME, messageName);
        message.addPayload(errorMessage);
        return this.incidentMessagePort.sendIncidentMessage(message, this.incidentDestination);
    }

    @Override
    public boolean handleTechnicalError(final String processInstanceId, final String errorCode, final String errorMessage) {
        log.warn("A technical error occured for process {} with error message {}", processInstanceId, errorMessage);
        final TechnicalErrorDto payload = TechnicalErrorDto.builder()
                .processInstanceId(processInstanceId)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .messageName(this.technicalErrorDestination)
                .build();
        final Message<TechnicalErrorDto> message = new Message<>();
        message.addHeader(TYPE, this.technicalErrorDestination);
        message.addPayload(payload);
        return this.technicalErrorMessagePort.sendTechnicalErrorMessage(message, this.technicalErrorDestination);
    }

}
