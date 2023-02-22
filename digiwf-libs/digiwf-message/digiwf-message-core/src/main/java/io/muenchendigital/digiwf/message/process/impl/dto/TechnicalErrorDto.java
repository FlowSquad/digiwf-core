package io.muenchendigital.digiwf.message.process.impl.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TechnicalErrorDto {
    private String processInstanceId;
    private String messageName;
    private String errorCode;
    private String errorMessage;

    void update(final String processInstanceId, final String messageName, final String errorCode, final String errorMessage) {
        this.processInstanceId = processInstanceId;
        this.messageName = messageName;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
