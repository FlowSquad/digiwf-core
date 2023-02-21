package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;

public interface CorrelateMessagePort {
    boolean sendCorrelateMessage(Message<CorrelateMessageDto> message, String destination);
}
