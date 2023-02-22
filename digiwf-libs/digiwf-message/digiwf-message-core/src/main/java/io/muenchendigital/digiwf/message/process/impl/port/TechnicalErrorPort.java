package io.muenchendigital.digiwf.message.process.impl.port;

import io.muenchendigital.digiwf.message.process.impl.dto.TechnicalErrorDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;

public interface TechnicalErrorPort {

    boolean sendTechnicalErrorMessage(Message<TechnicalErrorDto> message, String destination);

}
