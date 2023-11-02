package de.muenchen.oss.digiwf.message.core.impl;


import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MessageApiImplTest {

    private final Sinks.Many<org.springframework.messaging.Message<Object>> messageSink = Mockito.spy(Mockito.mock(Sinks.Many.class));
    @InjectMocks
    private final MessageApi messageApi = new MessageApiImpl(this.messageSink);

    @Test
    void testSendMessage() {
        final ArgumentCaptor<Message<Object>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        when(this.messageSink.tryEmitNext(messageCaptor.capture())).thenReturn(Sinks.EmitResult.OK);

        final boolean success = this.messageApi.sendMessage("test", "test");
        Assertions.assertTrue(success);

        // check message object that should be sent
        Assertions.assertEquals("test", messageCaptor.getValue().getPayload());
        Assertions.assertEquals("test", messageCaptor.getValue().getHeaders().get("spring.cloud.stream.sendto.destination"));
    }

    @Test
    void testSendMessageFails() {
        when(this.messageSink.tryEmitNext(any())).thenReturn(Sinks.EmitResult.FAIL_TERMINATED);
        final boolean success = this.messageApi.sendMessage("test", "test");
        Assertions.assertFalse(success);
    }

    @Test
    void testSendMessageWithCustomHeaders() {
        final ArgumentCaptor<Message<Object>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        when(this.messageSink.tryEmitNext(messageCaptor.capture())).thenReturn(Sinks.EmitResult.OK);

        final Map<String, Object> headers = Map.of("key", "value");
        final boolean success = this.messageApi.sendMessage("test", headers, "test");
        Assertions.assertTrue(success);

        // check message object that should be sent
        Assertions.assertEquals("test", messageCaptor.getValue().getPayload());
        Assertions.assertEquals("test", messageCaptor.getValue().getHeaders().get("spring.cloud.stream.sendto.destination"));
        Assertions.assertEquals("value", messageCaptor.getValue().getHeaders().get("key"));
    }

    @Test
    void testSendMessageFailsIfDestinationisNull() {
        boolean success = this.messageApi.sendMessage("test", null);
        Assertions.assertFalse(success);

        // with headers
        success = this.messageApi.sendMessage("test", Map.of("test", "test"), null);
        Assertions.assertFalse(success);
    }

    @Test
    void testSendMessageFailsIfPayloadIsNull() {
        boolean success = this.messageApi.sendMessage(null, "test");
        Assertions.assertFalse(success);

        // with headers
        success = this.messageApi.sendMessage(null, Map.of("test", "test"), "test");
        Assertions.assertFalse(success);
    }

}
