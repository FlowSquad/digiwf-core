package io.muenchendigital.digiwf.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

@RequiredArgsConstructor
@ComponentScan(basePackages = { "io.muenchendigital.digiwf.integration.core", "io.muenchendigital.digiwf.integration.adapter" })
public class DigiwfIntegrationAutoConfiguration {

}
