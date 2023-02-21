package io.muenchendigital.digiwf.message.core.api;

public interface IncidentApi {

    boolean handleIncident(String processInstanceId, String messageName, String errorMessage);

}
