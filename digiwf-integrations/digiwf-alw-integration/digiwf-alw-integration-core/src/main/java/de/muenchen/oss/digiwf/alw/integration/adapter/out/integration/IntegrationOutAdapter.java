package de.muenchen.oss.digiwf.alw.integration.adapter.out.integration;

import de.muenchen.oss.digiwf.alw.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.message.common.MessageConstants;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class IntegrationOutAdapter implements IntegrationOutPort {
  private final ProcessApi processApi;
  private final ErrorApi errorApi;

  @Override
  public void correlateProcessMessage(@NonNull MessageHeaders headers, Map<String, Object> payload) {
    final String processInstanceId = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)).toString();
    final String integrationName = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_INTEGRATION_NAME)).toString();
    final String type = Objects.requireNonNull(headers.get(MessageConstants.TYPE)).toString();
    if (payload == null) {
      payload = new HashMap<>();
    }
    this.processApi.correlateMessage(processInstanceId, type, integrationName, payload);
  }

  @Override
  public void handleBpmnError(@NonNull MessageHeaders headers, @NonNull BpmnError bpmnError) {
    this.errorApi.handleBpmnError(headers, bpmnError);
  }

  @Override
  public void handleIncident(@NonNull MessageHeaders headers, @NonNull IncidentError incidentError) {
    this.errorApi.handleIncident(headers, incidentError);
  }
}
