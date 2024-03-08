package de.muenchen.oss.digiwf.jsonschema.deployment.api.streaming;

import de.muenchen.oss.digiwf.deployment.domain.model.DeploymentStatusModel;
import de.muenchen.oss.digiwf.jsonschema.deployment.api.mapper.SchemaDeploymentMapper;
import de.muenchen.oss.digiwf.jsonschema.deployment.api.streaming.event.SchemaDeploymentEvent;
import de.muenchen.oss.digiwf.jsonschema.deployment.domain.service.SchemaDeploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;


@Deprecated
@Slf4j
@Component
@RequiredArgsConstructor
public class SchemaDeploymentEventListener {

    private final Sinks.Many<Message<DeploymentStatusModel>> statusEmitter;
    private final SchemaDeploymentService modelDeploymentService;
    private final SchemaDeploymentMapper mapper;

    @Deprecated
    @Bean
    public Consumer<Message<SchemaDeploymentEvent>> deploySchema() {
        return message -> {
            final SchemaDeploymentEvent deploymentEvent = message.getPayload();
            log.info("Sent deployment event with deploymentId: {}, versionId: {}, target: {}, artifactType: {}", deploymentEvent.getDeploymentId(), deploymentEvent.getVersionId(), deploymentEvent.getTarget(), deploymentEvent.getArtifactType());
            // trigger deployment
            final DeploymentStatusModel status = this.modelDeploymentService.deploy(this.mapper.mapTo(deploymentEvent));

            final Message<DeploymentStatusModel> statusMessage = MessageBuilder
                    .withPayload(status)
                    .build();

            this.statusEmitter.tryEmitNext(statusMessage).orThrow();
            log.info("Sent deployment status event for deployment {}", status.getDeploymentId());
        };
    }
}
