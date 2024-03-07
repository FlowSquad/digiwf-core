package de.muenchen.oss.digiwf.process.config.api.streaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.deployment.api.enums.DeploymentStatus;
import de.muenchen.oss.digiwf.deployment.api.streaming.event.DeploymentEvent;
import de.muenchen.oss.digiwf.deployment.domain.model.DeploymentStatusModel;
import de.muenchen.oss.digiwf.process.config.api.mapper.ProcessConfigApiMapper;
import de.muenchen.oss.digiwf.process.config.api.transport.ProcessConfigTO;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConfigurationDeploymentEventListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final ProcessConfigService processConfigService;
    private final ProcessConfigApiMapper processConfigApiMapper;
    private final Sinks.Many<Message<DeploymentStatusModel>> statusEmitter;

    @Bean
    public Consumer<Message<DeploymentEvent>> deployConfiguration() {
        return message -> {
            final DeploymentEvent deploymentEvent = message.getPayload();
            log.info("Sent deployment event with deploymentId: {}, versionId: {}, target: {}, artifactType: {}", deploymentEvent.getDeploymentId(), deploymentEvent.getVersionId(), deploymentEvent.getTarget(), deploymentEvent.getArtifactType());

            try {
                // deserialize
                final ProcessConfigTO config = this.objectMapper.readValue(deploymentEvent.getFile(), ProcessConfigTO.class);
                // validation
                final Set<ConstraintViolation<ProcessConfigTO>> violations = this.validatorFactory.getValidator().validate(config);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }
                // save config
                this.processConfigService.saveProcessConfig(this.processConfigApiMapper.map(config));

                // send deployment status event
                final Message<DeploymentStatusModel> statusMessage = MessageBuilder
                        .withPayload(new DeploymentStatusModel(DeploymentStatus.SUCCESSFUL.getValue(), deploymentEvent.getDeploymentId(), "Deployment was successful!"))
                        .build();
                this.statusEmitter.tryEmitNext(statusMessage).orThrow();
                log.debug("Sent deployment status event for deployment {}", deploymentEvent.getDeploymentId());
            } catch (final IOException | ConstraintViolationException e) {
                // error handling and send deployment status event
                log.debug(e.getMessage(), e);
                final Message<DeploymentStatusModel> statusMessage = MessageBuilder
                        .withPayload(new DeploymentStatusModel(DeploymentStatus.FAILURE.getValue(), deploymentEvent.getDeploymentId(), e.getMessage()))
                        .build();
                this.statusEmitter.tryEmitNext(statusMessage).orThrow();
            }
        };
    }
}
