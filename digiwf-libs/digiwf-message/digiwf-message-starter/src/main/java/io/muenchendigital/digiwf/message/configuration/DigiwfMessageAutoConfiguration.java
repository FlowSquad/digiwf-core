package io.muenchendigital.digiwf.message.configuration;


import io.muenchendigital.digiwf.message.properties.DigiwfMessageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.message")
@EnableConfigurationProperties(DigiwfMessageProperties.class)
public class DigiwfMessageAutoConfiguration {

}
