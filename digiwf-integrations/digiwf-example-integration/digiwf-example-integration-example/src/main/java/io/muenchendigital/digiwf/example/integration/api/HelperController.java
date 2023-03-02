package io.muenchendigital.digiwf.example.integration.api;

import io.muenchendigital.digiwf.integration.adapter.shared.StreamingHeaders;
import io.muenchendigital.digiwf.message.core.api.MessageApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HelperController {

    private final MessageApi messageApi;

    /**
     * Example Controller to send a test message to the {@link io.muenchendigital.digiwf.example.integration.core.ExampleIntegration} integration.
     */
    @GetMapping
    public void test() {
        final String targetTopic = "digiwf-example-integration";

        final Map<String, Object> headers = Map.of(
                StreamingHeaders.TYPE, "example-integration",
                StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID, "123456789",
                StreamingHeaders.DIGIWF_MESSAGE_NAME, "test"
        );

        this.messageApi.sendMessage(
                Map.of("brand", "VW", "model", "Golf"),
                headers,
                targetTopic
        );

        // should raise a techicalerror
        this.messageApi.sendMessage(
                Map.of("brand", "BMW", "model", "3"),
                headers,
                targetTopic
        );
    }
}
