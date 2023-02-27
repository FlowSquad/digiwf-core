package io.muenchendigital.digiwf.integration.adapter.api;

import io.muenchendigital.digiwf.integration.adapter.shared.StreamingHeaders;
import io.muenchendigital.digiwf.integration.core.api.IntegrationExecuteApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class InputAdapter {
    private final IntegrationExecuteApi integrationExecuteApi;

    @Bean
    public Consumer<Message<Map<String,Object>>> receiveMessages() {
        return message -> {
            final Object result = this.integrationExecuteApi.execute(message.getHeaders().get(StreamingHeaders.TYPE).toString(), message.getPayload());
            System.out.println("Yay");
        };
    }

}
