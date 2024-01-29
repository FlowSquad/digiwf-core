package de.muenchen.oss.digiwf.connector.core.adapter.out;


import de.muenchen.oss.digiwf.connector.BaseSpringTest;
import de.muenchen.oss.digiwf.connector.core.application.port.out.EmitEventOutPort;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Streaming Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventEmitterAdapterTest extends BaseSpringTest {


    @Mock
    private Sinks.Many<Message<Map<String, Object>>> dynamicSink;

    private EmitEventOutPort outputService;

    @BeforeEach
    private void initTests() {
        this.outputService = new EventEmitterAdapter(this.dynamicSink);
    }

    @Order(1)
    @Test
    @DisplayName("shouldEmitMessage")
    void shouldEmitMessage() {

        when(this.dynamicSink.tryEmitNext(any())).thenReturn(Sinks.EmitResult.OK);

        this.outputService.emitEvent("myTopic", "myType", "exampleIntegration", "myInstance", "processDefinition", Map.of("key", "value"));

        verify(this.dynamicSink, times(1)).tryEmitNext(any());
    }

}
