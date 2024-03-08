package de.muenchen.oss.digiwf.deployment.api.streaming;

import de.muenchen.oss.digiwf.deployment.domain.model.DeploymentStatusModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Deprecated
@Configuration
public class DeploymentEventEmitter {

    @Deprecated
    @Bean
    public Sinks.Many<Message<DeploymentStatusModel>> deploymentStatusSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Deprecated
    @Bean
    public Supplier<Flux<Message<DeploymentStatusModel>>> deploymentStatus(final Sinks.Many<Message<DeploymentStatusModel>> sink) {
        return sink::asFlux;
    }
}
