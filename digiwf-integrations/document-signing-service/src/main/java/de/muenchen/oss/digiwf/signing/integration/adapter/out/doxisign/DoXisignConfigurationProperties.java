package de.muenchen.oss.digiwf.signing.integration.adapter.out.doxisign;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "digiwf.signing.doxisign")
public class DoXisignConfigurationProperties {
    private String host;
}
