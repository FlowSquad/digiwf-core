package de.muenchen.oss.digiwf.connector.core.application.port.out;

import reactor.util.annotation.Nullable;

public interface CreateIncidentOutPort {

    /**
     * Create a incident
     *
     * @param processInstanceId id of the process instance
     * @param typeHeader        type header of the integration
     * @param messageContent    optional content of message for provide detailed information
     */
    void createIncident(String processInstanceId, String typeHeader, @Nullable String messageContent);

}
