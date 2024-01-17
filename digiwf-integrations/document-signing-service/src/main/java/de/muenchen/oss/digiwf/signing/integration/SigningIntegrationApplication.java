package de.muenchen.oss.digiwf.signing.integration;

import de.muenchen.oss.digiwf.signing.integration.adapter.out.doxisign.DoXisignConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({DoXisignConfigurationProperties.class})
public class SigningIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigningIntegrationApplication.class, args);
    }

}
