package io.muenchendigital.digiwf.message.process.impl.port;

import io.muenchendigital.digiwf.message.process.impl.model.Message;

public interface IncidentPort {

    boolean sendIncidentMessage(Message<Object> message, String destination);

}
