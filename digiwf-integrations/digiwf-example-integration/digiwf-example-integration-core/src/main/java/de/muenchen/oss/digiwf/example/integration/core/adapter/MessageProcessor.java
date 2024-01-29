package de.muenchen.oss.digiwf.example.integration.core.adapter;

import de.muenchen.oss.digiwf.example.integration.core.application.in.ExampleUseCase;
import de.muenchen.oss.digiwf.example.integration.core.application.out.CorrelateMessagePort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@Configuration
@RequiredArgsConstructor
public class MessageProcessor implements CorrelateMessagePort {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final ExampleUseCase exampleUseCase;
    private final ExampleMapper exampleMapper;

    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> {
            try {
                final ExampleDto exampleDto = message.getPayload();
                this.exampleUseCase.processExampleData(this.exampleMapper.toModel(exampleDto));

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(TYPE).toString(),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME).toString(), Map.of("someData", exampleDto.getSomeData()));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Override
    public void correlateMessage(final String processInstanceId, final String type, final String integrationName, final Map<String, Object> message) {
        this.processApi.correlateMessage(processInstanceId, type, integrationName, message);
    }

}
