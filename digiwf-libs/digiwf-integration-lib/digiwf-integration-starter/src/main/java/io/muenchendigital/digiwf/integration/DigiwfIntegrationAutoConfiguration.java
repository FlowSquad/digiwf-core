package io.muenchendigital.digiwf.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

/**
 * Autoconfiguration for the digiwf integration library that scans the core and adapter packages.
 */
@RequiredArgsConstructor
@ComponentScan(basePackages = { "io.muenchendigital.digiwf.integration.core", "io.muenchendigital.digiwf.integration.adapter" })
public class DigiwfIntegrationAutoConfiguration {

}
