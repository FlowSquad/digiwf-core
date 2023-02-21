package io.muenchendigital.digiwf.message.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.message")
public class DigiwfMessageProperties {
    private String incidentDestination;
    private String technicalErrorDestination;
    private String correlateMessageDestination;
    private String startProcessDestination;
}
