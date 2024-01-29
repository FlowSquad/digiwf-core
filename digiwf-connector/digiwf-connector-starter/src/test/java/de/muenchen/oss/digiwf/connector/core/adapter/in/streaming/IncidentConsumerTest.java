package de.muenchen.oss.digiwf.connector.core.adapter.in.streaming;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CreateIncidentInPort;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@DisplayName("Incident Consumer Test")
class IncidentConsumerTest {
    private static final String HEADER_PROCESS_INSTANCE_ID = "digiwf.processinstanceid";
    public static final String HEADER_INTEGRATION_NAME = "digiwf.integrationname";

    private final CreateIncidentInPort inPort = mock(CreateIncidentInPort.class);

    private final IncidentConsumer incidentConsumer = new IncidentConsumer(inPort);

    @Test
    @DisplayName("should do noting if headers are empty")
    void shouldDoNothingIfHeadersAreEmpty() {
        Consumer<Message<String>> consumer = incidentConsumer.createIncident();

        consumer.accept(new Message<String>() {
            @Override
            public @NotNull String getPayload() {
                return "payload";
            }

            @Override
            public @NotNull MessageHeaders getHeaders() {
                return new MessageHeaders(new HashMap<>());
            }
        });

        verifyNoInteractions(inPort); //.createIncident(anyString(), anyString(), anyString()));
    }

    @Test
    @DisplayName("should create incident if headers are given")
    void shouldCreateIncidentIfHeadersAreGiven() {
        Consumer<Message<String>> consumer = incidentConsumer.createIncident();

        consumer.accept(new Message<String>() {
            @Override
            public @NotNull String getPayload() {
                return "payload";
            }

            @Override
            public @NotNull MessageHeaders getHeaders() {
                final HashMap<String, Object> map = new HashMap<>();
                map.put(HEADER_PROCESS_INSTANCE_ID, "process-instance-id");
                map.put(HEADER_INTEGRATION_NAME, "integration-name");

                return new MessageHeaders(map);
            }
        });

        verify(inPort).createIncident("process-instance-id", "integration-name", "payload");
    }

    @Test
    @DisplayName("should create incident if payload is missing")
    void shouldCreateIncidentIfPayloadIsMissing() {
        Consumer<Message<String>> consumer = incidentConsumer.createIncident();

        consumer.accept(new Message<String>() {
            @Override
            public String getPayload() {
                return null;
            }

            @Override
            public @NotNull MessageHeaders getHeaders() {
                final HashMap<String, Object> map = new HashMap<>();
                map.put(HEADER_PROCESS_INSTANCE_ID, "process-instance-id");
                map.put(HEADER_INTEGRATION_NAME, "integration-name");

                return new MessageHeaders(map);
            }
        });

        verify(inPort).createIncident("process-instance-id", "integration-name", null);
    }
}
