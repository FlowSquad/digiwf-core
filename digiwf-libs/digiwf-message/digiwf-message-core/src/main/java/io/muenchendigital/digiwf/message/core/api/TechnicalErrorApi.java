package io.muenchendigital.digiwf.message.core.api;

public interface TechnicalErrorApi {

    boolean handleTechnicalError(String processInstanceId, String errorCode, String errorMessage);

}
