package io.muenchendigital.digiwf.example;

import io.muenchendigital.digiwf.integration.core.api.DigiwfIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ExampleIntegration {

    @DigiwfIntegration(type = "correlatemessagev01")
    public Map<String, String> exampleIntegration(final Object dto) {
        log.info(dto.toString());
        return new HashMap<String, String>() {{
            this.put("key1", "value1");
            this.put("key2", "value2");
        }};
    }

}
