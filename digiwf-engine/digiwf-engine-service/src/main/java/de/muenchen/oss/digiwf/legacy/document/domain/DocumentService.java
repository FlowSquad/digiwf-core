/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.document.domain;

import de.muenchen.oss.digiwf.humantask.domain.service.HumanTaskService;
import de.muenchen.oss.digiwf.legacy.document.external.client.CosysClient;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionService;
import de.muenchen.oss.digiwf.shared.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static de.muenchen.oss.digiwf.task.TaskVariables.TASK_ASSIGNEE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final CosysClient cosysClient;
    private final ServiceDefinitionService processDefinitionService;
    private final HumanTaskService taskService;
    private final TaskService camundaTaskService;
    private final ProcessConfigService processConfigService;

    private final RuntimeService runtimeService;

    public byte[] createDocument(final String documentGuid, final Map<String, Object> variables) {
        return this.cosysClient.generateDocument(documentGuid, variables);
    }

    public byte[] createDocument(final String documentGuid, final String executionId) {
        return this.cosysClient.generateDocument(documentGuid, this.runtimeService.getVariables(executionId));
    }

    public byte[] getStatusDokumentForTask(final String taskId, final String userId) {
        final Task task = this.taskService.getTask(taskId);
        val assignedUserId = TASK_ASSIGNEE.from(camundaTaskService, taskId).getLocalOptional().orElseGet(task::getAssignee);
        if (!userId.equals(assignedUserId)) {
            throw new ObjectNotFoundException(String.format("The task with the id %s is not available.", taskId));
        }

        final ProcessDefinition processDefinition = this.processDefinitionService.getServiceDefinition(task.getProcessDefinitionId());
        final String statusDokument = this.processConfigService.getProcessConfig(processDefinition.getKey())
                .orElseThrow(() -> new ObjectNotFoundException("No Status document available"))
                .getStatusDokument();
        return this.createDocument(statusDokument, this.runtimeService.getVariables(task.getProcessInstanceId()));
    }
}
