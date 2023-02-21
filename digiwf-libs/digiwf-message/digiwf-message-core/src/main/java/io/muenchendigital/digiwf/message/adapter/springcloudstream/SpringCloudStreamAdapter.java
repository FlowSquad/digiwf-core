package io.muenchendigital.digiwf.message.adapter.springcloudstream;

import io.muenchendigital.digiwf.message.common.Adapter;
import io.muenchendigital.digiwf.message.core.impl.SendMessagePort;
import io.muenchendigital.digiwf.message.process.impl.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.impl.StartProcessPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class SpringCloudStreamAdapter implements SendMessagePort, StartProcessPort, CorrelateMessagePort {

    private final Sinks.Many<org.springframework.messaging.Message<Object>> messageSink;

    public static final String SPRING_CLOUD_STREAM_SENDTO_DESTINATION = "spring.cloud.stream.sendto.destination";

    @Override
    public boolean sendCorrelateMessage(final io.muenchendigital.digiwf.message.process.impl.model.Message message, final String destination) {
        return this.sendMessageToDestination(message.getPayload(), message.getHeaders(), destination);
    }

    @Override
    public boolean startProcess(final io.muenchendigital.digiwf.message.process.impl.model.Message message, final String destination) {
        return this.sendMessageToDestination(message.getPayload(), message.getHeaders(), destination);
    }

    @Override
    public boolean sendMessage(final io.muenchendigital.digiwf.message.core.impl.model.Message message, final String destination) {
        return this.sendMessageToDestination(message.getPayload(), message.getHeaders(), destination);
    }

    private boolean sendMessageToDestination(final Object payload, final Map<String, Object> headers, final String destination) {
        final MessageHeaders scsHeaders = new MessageHeaders(headers);

        // send the message to the given destination
        headers.put(SPRING_CLOUD_STREAM_SENDTO_DESTINATION, destination);

        final Message<Object> message = MessageBuilder.createMessage(payload, scsHeaders);
        final Sinks.EmitResult emitResult = this.messageSink.tryEmitNext(message);
        if (emitResult.isSuccess()) {
            log.info("The message {} was successfully delivered to the eventbus (topic {}).", message.getHeaders().get(MessageHeaders.ID), destination);
        } else {
            log.error("The message {} couldn't be delivered to the eventbus (topic {}).", message.getHeaders().get(MessageHeaders.ID), destination);
        }
        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }
}
