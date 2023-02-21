package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.process.api.CorrelateMessageApi;
import io.muenchendigital.digiwf.message.process.api.StartProcessApi;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static io.muenchendigital.digiwf.message.common.MessageConstants.*;

@RequiredArgsConstructor
public class ProcessService implements StartProcessApi, CorrelateMessageApi {

    private final String correlateMessageDestination;
    private final String startProcessDestination;

    private final CorrelateMessagePort correlateMessagePort;
    private final StartProcessPort startProcessPort;

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
        final Message<StartProcessDto> message = new Message();
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
        final Message<CorrelateMessageDto> message = new Message();
        message.addPayload(payload);
        message.addHeader(TYPE, CORRELATEMESSAGEV_01);
        message.addHeader(DIGIWF_PROCESS_INSTANCE_ID, processInstanceId);
        message.addHeader(DIGIWF_MESSAGE_NAME, messageName);
        return this.correlateMessagePort.sendCorrelateMessage(message, this.correlateMessageDestination);
    }

}
