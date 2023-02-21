package io.muenchendigital.digiwf.message.process.api;

import java.util.Map;

public interface CorrelateMessageApi {

    boolean correlateMessage(String processInstanceId, String messageName, Map<String, Object> payloadVariables);

}
