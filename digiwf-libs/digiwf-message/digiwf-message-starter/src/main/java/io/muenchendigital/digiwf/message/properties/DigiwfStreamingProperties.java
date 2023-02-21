package io.muenchendigital.digiwf.message.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Setter
@Getter
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.streaming")
public class DigiwfStreamingProperties {
    @Nullable
    private Map<String, @NotBlank String> typeMappings;
}
