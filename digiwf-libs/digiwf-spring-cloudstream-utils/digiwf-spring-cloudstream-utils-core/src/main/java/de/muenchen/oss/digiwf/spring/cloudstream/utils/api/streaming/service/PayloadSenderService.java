package de.muenchen.oss.digiwf.spring.cloudstream.utils.api.streaming.service;

import de.muenchen.oss.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Deprecated
public class PayloadSenderService {

    private final Sinks.Many<Message<Object>> messageSink;

    /**
     * Sends a payload. Can be used if you don't want to set any other MessageHeaders than {@link StreamingHeaders}.TYPE.
     * @param payload Payload to send in the message.
     * @param type Type of the message, used to determine how the message gets handled on the receiving side.
     *
     * This function is deprecated. Use digiwf-message instead {@link de.muenchen.oss.digiwf:digiwf-message:0.18.0}.
     * @deprecated This function is no longer supported and may be removed in a future release
     *
     * @return true when message has been sent, false when not
     */
    @Deprecated
    public boolean sendPayload(final Object payload, final String type) {
        final Map<String, Object> headers = new HashMap<>();
        headers.put(StreamingHeaders.TYPE, type);
        final MessageHeaders messageHeaders = new MessageHeaders(headers);
        return this.sendPayload(payload, messageHeaders);
    }

    /**
     * Sends a payload. Can be used if you want to set all MessageHeaders of the outgoing message yourself.
     * @param payload Payload to send in the message.
     * @param messageHeaders Headers to use in the message.
     * @return true when message has been sent, false when not
     */
    @Deprecated
    public boolean sendPayload(final Object payload, final MessageHeaders messageHeaders) {
        final Message<Object> message = MessageBuilder.createMessage(payload, messageHeaders);
        final Sinks.EmitResult emitResult = this.messageSink.tryEmitNext(message);

        if (emitResult.isSuccess()) {
            log.info("The message {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The message {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }
        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}
