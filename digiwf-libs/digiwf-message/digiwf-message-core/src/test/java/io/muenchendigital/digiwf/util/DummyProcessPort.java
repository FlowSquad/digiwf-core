package io.muenchendigital.digiwf.util;

import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.dto.TechnicalErrorDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;
import io.muenchendigital.digiwf.message.process.impl.port.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.impl.port.IncidentPort;
import io.muenchendigital.digiwf.message.process.impl.port.StartProcessPort;
import io.muenchendigital.digiwf.message.process.impl.port.TechnicalErrorPort;

public class DummyProcessPort  implements StartProcessPort, CorrelateMessagePort, IncidentPort, TechnicalErrorPort {

    @Override
    public boolean sendCorrelateMessage(final Message<CorrelateMessageDto> message, final String destination) {
        return true;
    }

    @Override
    public boolean sendIncidentMessage(final Message<Object> message, final String destination) {
        return true;
    }

    @Override
    public boolean startProcess(final Message<StartProcessDto> message, final String destination) {
        return true;
    }

    @Override
    public boolean sendTechnicalErrorMessage(final Message<TechnicalErrorDto> message, final String destination) {
        return true;
    }
}
