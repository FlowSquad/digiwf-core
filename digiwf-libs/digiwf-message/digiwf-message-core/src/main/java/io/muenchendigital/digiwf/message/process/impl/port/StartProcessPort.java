package io.muenchendigital.digiwf.message.process.impl.port;

import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;

public interface StartProcessPort {
    boolean startProcess(Message<StartProcessDto> message, String destination);
}
