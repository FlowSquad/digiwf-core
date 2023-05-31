package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.muenchendigital.digiwf.task.BpmnErrors;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import lombok.val;
import org.camunda.bpm.engine.TaskService;
import org.camunda.community.mockito.QueryMocks;
import org.camunda.community.mockito.task.TaskFake;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RemoteTaskCommandRestAdapterTest {
    private final TaskService taskService = mock(TaskService.class);
    private final TaskCommandPort taskCommandPort = new RemoteTaskCommandRestAdapter(taskService);
    private final String taskId = UUID.randomUUID().toString();

    private final VariableFactory<String> STRING_VAR = stringVariable("STRING_VAR");

    @Test
    public void cancels_task() {
        taskCommandPort.cancelUserTask(taskId);
        verify(taskService).handleBpmnError(taskId, BpmnErrors.DEFAULT_TASK_CANCELLATION_ERROR);
    }

    @Test
    public void assign() {
        taskCommandPort.assignUserTask(taskId, "user");
        verify(taskService).setAssignee(taskId, "user");
    }

    @Test
    public void unassign() {
        taskCommandPort.unassignUserTask(taskId);
        verify(taskService).setAssignee(taskId, null);
    }

    @Test
    public void complete() {
        val vars = CamundaBpmData
                .builder()
                .set(STRING_VAR, "init")
                .build();
        taskCommandPort.completeUserTask(taskId, vars);
        verify(taskService).complete(taskId, vars);
    }

    @Test
    public void save() {
        val vars = CamundaBpmData
                .builder()
                .set(STRING_VAR, "init")
                .build();
        taskCommandPort.saveUserTask(taskId, vars);
        verify(taskService).setVariables(taskId, vars);
    }

    @Test
    public void deferUserTask() {
        val instant = LocalDateTime.of(2023, 5, 24, 12, 0).toInstant(ZoneOffset.UTC);
        final TaskFake task = TaskFake.builder().build();
        QueryMocks.mockTaskQuery(taskService).singleResult(task);

        taskCommandPort.deferUserTask(taskId, instant);
        assertThat(task.getFollowUpDate()).isNotNull(); // check only if there is an interaction, no Instant to DateTime transformation is tested
        verify(taskService).saveTask(task);
    }
    @Test
    public void undeferUserTask() {
        final TaskFake task = TaskFake.builder().build();
        QueryMocks.mockTaskQuery(taskService).singleResult(task);

        taskCommandPort.undeferUserTask(taskId);
        assertThat(task.getFollowUpDate()).isNull(); // check only if there is an interaction, no Instant to DateTime transformation is tested
        verify(taskService).saveTask(task);
    }
}
