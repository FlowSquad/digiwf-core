package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.core.impl.model.Message;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static io.muenchendigital.digiwf.message.common.MessageConstants.TYPE;

@RequiredArgsConstructor
public class MessageApiImpl implements MessageApi {

    private final SendMessagePort sendMessagePort;

    @Override
    public boolean sendMessage(final Object payload, final String destination) {
        final Message message = new Message();
        message.addHeader(TYPE, destination);
        message.addPayload(payload);
        return this.sendMessagePort.sendMessage(message, destination);
    }

    @Override
    public boolean sendMessage(final Object payload, final Map<String, Object> headers, final String destination) {
        final Message message = new Message();
        message.addHeader(TYPE, destination);
        for (final Map.Entry<String, Object> entry : headers.entrySet()) {
            message.addHeader(entry.getKey(), entry.getValue().toString());
        }
        message.addPayload(payload);
        return this.sendMessagePort.sendMessage(message, destination);
    }
}
