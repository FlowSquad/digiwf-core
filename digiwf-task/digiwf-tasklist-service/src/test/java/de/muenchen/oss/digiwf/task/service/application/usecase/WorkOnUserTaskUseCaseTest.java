package de.muenchen.oss.digiwf.task.service.application.usecase;

import com.google.common.collect.Sets;
import de.muenchen.oss.digiwf.task.TaskSchemaType;
import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.adapter.out.engine.LegacyFormValidationAdapter;
import de.muenchen.oss.digiwf.task.service.application.port.in.WorkOnUserTask;
import de.muenchen.oss.digiwf.task.service.application.port.out.cancellation.CancellationFlagOutPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.engine.LegacyPayloadTaskCommandPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.auth.User;
import de.muenchen.oss.digiwf.task.service.adapter.out.schema.VariableTaskSchemaResolverAdapter;
import de.muenchen.oss.digiwf.task.service.adapter.out.schema.VariableTaskSchemaTypeResolverAdapter;
import de.muenchen.oss.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.polyflow.TaskNotFoundException;
import de.muenchen.oss.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.JsonSchemaPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.JsonSchemaValidationPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.TaskSchemaTypeResolverPort;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;

import static de.muenchen.oss.digiwf.task.service.application.usecase.TestFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WorkOnUserTaskUseCaseTest {

    private final TaskQueryPort taskQueryPort = mock(TaskQueryPort.class);
    private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort = new VariableTaskSchemaResolverAdapter();
    private final JsonSchemaPort jsonSchemaPort = mock(JsonSchemaPort.class);
    private final TaskCommandPort taskCommandPort = mock(TaskCommandPort.class);
    private final LegacyPayloadTaskCommandPort legacyTaskCommandPort = mock(LegacyPayloadTaskCommandPort.class);
    private final LegacyFormValidationAdapter legacyFormValidationAdapter = mock(LegacyFormValidationAdapter.class);
    private final JsonSchemaValidationPort jsonSchemaValidationPort = mock(JsonSchemaValidationPort.class);
    private final CancellationFlagOutPort cancellationFlagOutPort = mock(CancellationFlagOutPort.class);
    private final TaskSchemaTypeResolverPort taskSchemaTypeResolverPort = new VariableTaskSchemaTypeResolverAdapter();

    private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));
    private final WorkOnUserTask useCase = new WorkOnUserTaskUseCase(
            taskQueryPort,
            currentUserPort,
            taskSchemaRefResolverPort,
            jsonSchemaPort,
            taskCommandPort,
            legacyTaskCommandPort,
            legacyFormValidationAdapter,
            jsonSchemaValidationPort,
            cancellationFlagOutPort,
            taskSchemaTypeResolverPort
    );

    @BeforeEach
    void setupMocks() {
        when(currentUserPort.getCurrentUser()).thenReturn(user);
        when(cancellationFlagOutPort.apply(any())).thenReturn(true);
    }

    @Test
    void loads_schema() {
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(generateSchema("schema-1"));
        val schema = useCase.loadSchema("schema-1");
        assertThat(schema).isNotNull();
        assertThat(schema.getId()).isEqualTo("schema-1");

        verify(jsonSchemaPort).getSchemaById("schema-1");
        verifyNoMoreInteractions(jsonSchemaPort);

    }


    @Test
    void loads_non_existing_task() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenThrow(new TaskNotFoundException("not_exist"));
        val exception = assertThrows(TaskNotFoundException.class, () -> useCase.loadUserTask("not_exist"));
        assertThat(exception.getMessage()).isEqualTo("Task with id 'not_exist' could not be found.");
        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "not_exist");
        verifyNoMoreInteractions(taskQueryPort);

    }

    @Test
    void loads_existing_task() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(
                generateTask("task_0", Collections.emptySet(), Collections.emptySet(), null, null, true)
        );
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(generateSchema("schema-1"));

        val taskWithSchemaRef = useCase.loadUserTask("task_0");

        assertThat(taskWithSchemaRef.getSchemaRef()).isEqualTo("schema-1");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verify(jsonSchemaPort).getSchemaById("schema-1");
        verify(jsonSchemaValidationPort).filterVariables(any(), any());   //cannot be specific due to clearing of task.payload in filtering
        verifyNoMoreInteractions(taskQueryPort);
        verifyNoMoreInteractions(jsonSchemaPort);
        verifyNoMoreInteractions(jsonSchemaValidationPort);
        verifyNoInteractions(legacyTaskCommandPort);
    }

    @Test
    void loads_existing_task_with_legacy_form() {
        val form = generateForm("form-1");
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(
                generateTask("task_0", Collections.emptySet(), Collections.emptySet(), null, null, true, legacyFormVariables)
        );
        when(legacyTaskCommandPort.loadFormById(any())).thenReturn(form);

        val taskWithSchemaRef = useCase.loadUserTask("task_0");

        assertThat(taskWithSchemaRef.getSchemaRef()).isEqualTo("form-1");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verify(legacyTaskCommandPort).loadFormById("form-1");
        verify(legacyFormValidationAdapter).filterVariables(any(), any());   //cannot be specific due to clearing of task.payload in filtering
        verifyNoMoreInteractions(taskQueryPort);
        verifyNoMoreInteractions(legacyTaskCommandPort);
        verifyNoMoreInteractions(legacyFormValidationAdapter);
        verifyNoInteractions(jsonSchemaPort);
    }

    @Test
    void loadUserTaskWithSchema() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(generateSchema("schema-1"));
        val taskWithSchema = useCase.loadUserTaskWithSchema("task_0");

        assertThat(taskWithSchema.getTask()).isNotNull();
        assertThat(taskWithSchema.getSchema()).isNotNull();
        assertThat(taskWithSchema.getTask().getId()).isEqualTo("task_0");
        assertThat(taskWithSchema.getSchema().getId()).isEqualTo("schema-1");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(jsonSchemaPort).getSchemaById("schema-1");
        verifyNoMoreInteractions(jsonSchemaPort);
        verifyNoInteractions(legacyTaskCommandPort);
    }

    @Test
    void loadUserTaskWithLegacyForm() {
        val form = generateForm("form-1");
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername(), legacyFormVariables).get(0));
        when(legacyTaskCommandPort.loadFormById(any())).thenReturn(form);
        val taskWithSchema = useCase.loadUserTaskWithSchema("task_0");

        assertThat(taskWithSchema.getTask()).isNotNull();
        assertThat(taskWithSchema.getLegacyForm()).isNotNull();
        assertThat(taskWithSchema.getTask().getId()).isEqualTo("task_0");
        assertThat(taskWithSchema.getLegacyForm().getKey()).isEqualTo("form-1");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(legacyTaskCommandPort).loadFormById("form-1");
        verifyNoMoreInteractions(legacyTaskCommandPort);
        verifyNoInteractions(jsonSchemaPort);
    }


    @Test
    void completesTask() {
        val schema1 = generateSchema("schema-1");
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false,
                CamundaBpmData
                        .builder()
                        .set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.SCHEMA_BASED)
                        .build()
        );

        val payload = CamundaBpmData
                .builder()
                .set(STRING_VAL, "some")
                .set(INTEGER_VAL, 42)
                .build();


        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(task0);
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(schema1);
        when(jsonSchemaValidationPort.validateAndSerialize(any(), any(), any())).thenReturn(payload);

        useCase.completeUserTask("task_0", payload);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(jsonSchemaValidationPort).validateAndSerialize(schema1, task0, payload);
        verifyNoMoreInteractions(jsonSchemaValidationPort);

        verify(taskCommandPort).completeUserTask("task_0", payload);
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void savesTask() {
        val schema1 = generateSchema("schema-1");
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, true,
                CamundaBpmData
                        .builder()
                        .set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.SCHEMA_BASED)
                        .build()
        );

        val payload = CamundaBpmData
                .builder()
                .set(STRING_VAL, "some")
                .set(INTEGER_VAL, 42)
                .build();


        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(task0);
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(schema1);
        when(jsonSchemaValidationPort.validateAndSerialize(any(), any(), any())).thenReturn(payload);

        useCase.saveUserTask("task_0", payload);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(jsonSchemaValidationPort).validateAndSerialize(schema1, task0, payload);
        verifyNoMoreInteractions(jsonSchemaValidationPort);

        verify(taskCommandPort).saveUserTask("task_0", payload);
        verifyNoMoreInteractions(taskCommandPort);

    }

    @Test
    void completeTaskLegacy() {
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, true,
                CamundaBpmData
                        .builder()
                        .set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.VUETIFY_FORM_BASE)
                        .build()
        );

        val payload = CamundaBpmData
                .builder()
                .set(STRING_VAL, "some")
                .set(INTEGER_VAL, 42)
                .build();


        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(task0);

        useCase.completeUserTask("task_0", payload);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(legacyTaskCommandPort).completeOldSchemaUserTask("task_0", payload);

        verifyNoInteractions(jsonSchemaValidationPort);
        verifyNoInteractions(taskCommandPort);
        verifyNoInteractions(jsonSchemaPort);

    }


    @Test
    void savesTaskLegacy() {
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, true,
                CamundaBpmData
                        .builder()
                        .set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.VUETIFY_FORM_BASE)
                        .build()
        );

        val payload = CamundaBpmData
                .builder()
                .set(STRING_VAL, "some")
                .set(INTEGER_VAL, 42)
                .build();


        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(task0);

        useCase.saveUserTask("task_0", payload);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(legacyTaskCommandPort).saveOldSchemaUserTask("task_0", payload);

        verifyNoInteractions(jsonSchemaValidationPort);
        verifyNoInteractions(taskCommandPort);
        verifyNoInteractions(jsonSchemaPort);

    }

    @Test
    void assignsUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));

        useCase.assignUserTask("task_0", "4711");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).assignUserTask("task_0", "4711");
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void notAssignsUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));

        useCase.assignUserTask("task_0", user.getUsername());

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verifyNoInteractions(taskCommandPort);
    }


    @Test
    void unassignsUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));

        useCase.unassignUserTask("task_0");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).unassignUserTask("task_0");
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void deferesUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));

        val until = OffsetDateTime.now();
        useCase.deferUserTask("task_0", until);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).deferUserTask("task_0", until.toInstant());
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void undefersUserTask() {
        val until = Instant.now();

        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(
                generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), until)
        );

        useCase.undeferUserTask("task_0");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).undeferUserTask("task_0");
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void cancelsUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(
                generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, true)
        );
        useCase.cancelUserTask("task_0");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).cancelUserTask("task_0");
        verifyNoMoreInteractions(taskCommandPort);

    }

    @Test
    void failsToCancelUserTask() {
        when(cancellationFlagOutPort.apply(any())).thenReturn(false); // all tasks are not cancellable
        val task = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false);
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(task);

        val exception = assertThrows(IllegalArgumentException.class, () -> useCase.cancelUserTask("task_0"));
        assertThat(exception.getMessage()).isEqualTo("Task task_0 can not be cancelled.");

        verify(cancellationFlagOutPort).apply(task);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verifyNoInteractions(taskCommandPort);

    }

}
