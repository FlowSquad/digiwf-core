package de.muenchen.oss.digiwf.output.process;

import de.muenchen.oss.digiwf.engine.mapper.EngineDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.Map;

/**
 * This template is used by the platform Call Activity for Request / Reply Pattern.
 * Therefore variables of instance (Call Acivty) level are referenced.
 *
 * @author externer.dl.horn
 */
@Deprecated
@Slf4j
@Component
public class StreamingTemplateIntegrationDelegate extends AbstractStreamingIntegrationDelegate implements JavaDelegate {

    private final Sinks.Many<Message<Map<String, Object>>> dynamicSink;

    public StreamingTemplateIntegrationDelegate(final EngineDataMapper engineDataMapper, final Sinks.Many<Message<Map<String, Object>>> dynamicSink) {
        super(engineDataMapper);
        this.dynamicSink = dynamicSink;
    }

    /**
     * StreamingTemplateV01 sends an integration name to the integrations too, but it does not support any further features.
     * Migrate to StreamingTemplateV02 as soon as possible.
     *
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        final Map<String, Object> localVariables = delegateExecution.getVariables();
        log.debug("Send event to stream {}", localVariables);
        final Message<Map<String, Object>> message = this.mapMessage(delegateExecution, localVariables);
        this.dynamicSink.tryEmitNext(message).orThrow();
    }
}
