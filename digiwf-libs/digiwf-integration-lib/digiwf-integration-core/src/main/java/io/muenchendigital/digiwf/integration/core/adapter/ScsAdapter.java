package io.muenchendigital.digiwf.integration.core.adapter;

import io.muenchendigital.digiwf.integration.core.api.IntegrationExecuteApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ScsAdapter {
    private final IntegrationExecuteApi integrationExecuteApi;

    private final String TYPE = "type";
    private final String DIGIWF_MESSAGE_NAME = "digiwf.messagename";
    private final String DIGIWF_PROCESS_INSTANCE_ID = "digiwf.processinstanceid";

    @Bean
    public Consumer<Message<Map<String,Object>>> genericConsumer() {
        return message -> {
            final Object result = this.integrationExecuteApi.execute(message.getHeaders().get(this.TYPE).toString(), message.getPayload());
            System.out.println("Yay");
        };
    }

}
