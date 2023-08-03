package de.muenchen.oss.digiwf.example.integration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

@RequiredArgsConstructor
@ComponentScan(basePackages = { "de.muenchen.oss.digiwf.example.integration.core" })
public class ExampleIntegrationAutoconfiguration {

    // If you don't use the @ComponentScan annotation, you have to create the beans manually below.

}
