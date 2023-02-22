package example.shared;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.RoutingCallback;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CustomRoutingCallback extends RoutingCallback implements MessageRoutingCallback {

    public CustomRoutingCallback() {
        super(new HashMap<>());
    }

    @Override
    public FunctionRoutingResult routingResult(final Message<?> message) {
        // route everything to the processMessages consumer that prints out the message
        final String functionDefinition = "processMessage";
        return new FunctionRoutingResult(functionDefinition);
    }

}
