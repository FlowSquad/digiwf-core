package io.muenchendigital.digiwf.message.process.impl.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class CorrelateMessageDto {
    private String processInstanceId;
    private String messageName;
    private String businessKey;
    private Map<String, Object> payloadVariables;
    private Map<String, Object> payloadVariablesLocal;
}
