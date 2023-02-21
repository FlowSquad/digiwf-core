package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.common.Api;
import io.muenchendigital.digiwf.message.core.api.IncidentApi;
import io.muenchendigital.digiwf.message.core.api.SendMessageApi;
import io.muenchendigital.digiwf.message.core.api.TechnicalErrorApi;
import io.muenchendigital.digiwf.message.core.impl.dto.TechnicalErrorDto;
import io.muenchendigital.digiwf.message.core.impl.model.Message;
import lombok.RequiredArgsConstructor;

@Api
@RequiredArgsConstructor
public class MessageService implements SendMessageApi, TechnicalErrorApi, IncidentApi {


    private final SendMessagePort sendMessagePort;
    private final String incidentDestination;
    private final String technicalErrorDestination;

    public static final String TYPE = "type";
    public static final String DIGIWF_MESSAGE_NAME = "digiwf.messagename";
    public static final String DIGIWF_PROCESS_INSTANCE_ID = "digiwf.processinstanceid";

    @Override
    public boolean sendMessage(final Object payload, final String destination) {
        return this.sendMessagePort.sendMessage(this.buildMessage(payload, destination), destination);
    }

    @Override
    public boolean handleIncident(final String processInstanceId, final String messageName, final String errorMessage) {
        final Message message = this.buildMessage(errorMessage, this.incidentDestination);
        message.addHeader(DIGIWF_PROCESS_INSTANCE_ID, processInstanceId);
        message.addHeader(DIGIWF_MESSAGE_NAME, messageName);
        return this.sendMessagePort.sendMessage(message, this.incidentDestination);
    }

    @Override
    public boolean handleTechnicalError(final String processInstanceId, final String errorCode, final String errorMessage) {
        final TechnicalErrorDto payload = TechnicalErrorDto.builder()
                .processInstanceId(processInstanceId)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .messageName(this.technicalErrorDestination)
                .build();
        final Message message = this.buildMessage(payload, this.technicalErrorDestination);
        return this.sendMessagePort.sendMessage(message, this.technicalErrorDestination);
    }

    private Message buildMessage(final Object payload, final String type) {
        final Message message = new Message();
        message.addHeader(TYPE, type);
        message.addPayload(payload);
        return message;
    }
}
