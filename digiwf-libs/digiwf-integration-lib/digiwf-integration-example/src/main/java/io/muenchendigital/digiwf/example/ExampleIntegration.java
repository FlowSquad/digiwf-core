package io.muenchendigital.digiwf.example;

import io.muenchendigital.digiwf.integration.adapter.shared.StreamingHeaders;
import io.muenchendigital.digiwf.integration.core.api.DigiwfIntegration;
import io.muenchendigital.digiwf.integration.core.api.TechnicalError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ExampleIntegration {

    @DigiwfIntegration(type = "example-integration")
    public Map<String, String> exampleIntegration(final Map<String, Object> event) {
        final String processInstanceId = event.get(StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID).toString();
        final String messageName = event.get(StreamingHeaders.DIGIWF_MESSAGE_NAME).toString();
        log.info("Processing event for process instance id {} with message name {}", processInstanceId, messageName);

        if (event.get("brand").equals("BMW")) {
            throw new TechnicalError(processInstanceId, "500", "BMW is not allowed");
        }
        return Map.of(
                "status", "success",
                "message", "Vehicle is a " + event.get("brand") + " " + event.get("model")
        );
    }

}
