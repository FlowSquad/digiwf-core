package io.muenchendigital.digiwf.message.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Setter
@Getter
@Validated
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.message")
public class DigiwfMessageProperties {
    private String incidentDestination;
    private String technicalErrorDestination;
    private String correlateMessageDestination;
    private String startProcessDestination;
    @Nullable
    private Map<String, @NotBlank String> typeMappings;
}
