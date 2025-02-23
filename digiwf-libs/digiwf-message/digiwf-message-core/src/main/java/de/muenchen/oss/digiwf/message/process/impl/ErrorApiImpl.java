package de.muenchen.oss.digiwf.message.process.impl;

import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.message.process.impl.dto.BpmnErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@Slf4j
@RequiredArgsConstructor
public class ErrorApiImpl implements ErrorApi {
    private final MessageApi messageApi;
    private final String incidentDestination;
    private final String bpmnErrorDestination;

    private static final String BPMN_ERROR_MESSAGE_TYPE = "bpmnerror";
    private static final String BPMN_ERROR_MESSAGE_NAME = "bpmnError";

    /**
     * Handles an incident by sending a message to the incident destination.
     * The incident message contains the process instance id, message name and error message.
     *
     * @param originMessageHeaders The headers of the message that caused the incident.
     * @param errorMessage The error message to be passed to the process.
     * @return
     */
    @Override
    public boolean handleIncident(final Map<String, Object> originMessageHeaders, final String errorMessage) {
        final Map<String, Object> headers = Map.of(
                TYPE, originMessageHeaders.get(TYPE),
                DIGIWF_PROCESS_INSTANCE_ID, originMessageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID),
                DIGIWF_INTEGRATION_NAME, originMessageHeaders.get(DIGIWF_INTEGRATION_NAME)
        );
        return this.messageApi.sendMessage(errorMessage, headers, this.incidentDestination);
    }

    /**
     * Handles a bpmn error by sending a message to the bpmn error destination.
     * The bpmn error message contains the process instance id, error code and error message.
     *
     * @param originMessageHeaders The headers of the message that caused the bpmn error.
     * @param errorCode The error code to be passed to the process.
     * @param errorMessage The error message to be passed to the process.
     * @return
     */
    @Override
    public boolean handleBpmnError(final Map<String, Object> originMessageHeaders, final String errorCode, final String errorMessage) {
        final String processInstanceId = originMessageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID).toString();
        log.warn("A technical error occured for process {} with error message {}", processInstanceId, errorMessage);
        final BpmnErrorDto payload = BpmnErrorDto.builder()
                .processInstanceId(processInstanceId)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .messageName(BPMN_ERROR_MESSAGE_NAME)
                .build();
        final Map<String, Object> headers = Map.of(
                TYPE, BPMN_ERROR_MESSAGE_TYPE,
                DIGIWF_PROCESS_INSTANCE_ID, processInstanceId,
                DIGIWF_INTEGRATION_NAME, originMessageHeaders.get(DIGIWF_INTEGRATION_NAME)
        );
        return this.messageApi.sendMessage(payload, headers, this.bpmnErrorDestination);
    }

    @Override
    public boolean handleBpmnError(final Map<String, Object> originMessageHeaders, final BpmnError bpmnError) {
        if (!originMessageHeaders.containsKey(DIGIWF_PROCESS_INSTANCE_ID)) {
            throw new RuntimeException("The origin message headers do not contain a process instance id.");
        }
        return this.handleBpmnError(originMessageHeaders,
                bpmnError.getErrorCode(), bpmnError.getErrorMessage());
    }

    @Override
    public boolean handleIncident(final Map<String, Object> originMessageHeaders, final IncidentError incidentError) {
        if (!originMessageHeaders.containsKey(DIGIWF_PROCESS_INSTANCE_ID)) {
            throw new RuntimeException("The origin message headers do not contain a process instance id.");
        }
        if (!originMessageHeaders.containsKey(DIGIWF_INTEGRATION_NAME)) {
            throw new RuntimeException("The origin message headers do not contain an integration name.");
        }
        return this.handleIncident(originMessageHeaders, incidentError.getErrorMessage());
    }

}
