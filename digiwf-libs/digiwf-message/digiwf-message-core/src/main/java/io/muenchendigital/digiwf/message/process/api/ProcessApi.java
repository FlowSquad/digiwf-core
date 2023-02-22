package io.muenchendigital.digiwf.message.process.api;

import java.util.Map;

public interface ProcessApi {

    boolean startProcess(String processKey, Map<String, Object> variables);
    boolean startProcess(String processKey, Map<String, Object> variables, String fileContext);

    boolean correlateMessage(String processInstanceId, String messageName, Map<String, Object> payloadVariables);

    boolean handleIncident(String processInstanceId, String originMessageName, String errorMessage);

    boolean handleTechnicalError(String processInstanceId, String errorCode, String errorMessage);

}
