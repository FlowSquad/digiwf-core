package de.muenchen.oss.digiwf.task.service.adapter.in.rest.impl;

import de.muenchen.oss.digiwf.task.service.application.port.in.WorkOnUserTask;
import de.muenchen.oss.digiwf.task.service.application.port.in.rest.api.TaskApiDelegate;
import de.muenchen.oss.digiwf.task.service.adapter.in.rest.mapper.TaskMapper;
import de.muenchen.oss.digiwf.task.service.application.port.in.rest.model.*;
import de.muenchen.oss.digiwf.task.service.application.port.in.rest.model.TaskCombinedSchemaTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Task API delegate for work on one user task.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TaskApiDelegateImpl implements TaskApiDelegate {
  private final TaskMapper taskMapper;
  private final WorkOnUserTask workOnUserTask;

  @Override
  public ResponseEntity<TaskCombinedSchemaTO> getSchema(String schemaId) {
    val schema = workOnUserTask.loadSchema(schemaId);
    return ok(taskMapper.to(schema));
  }

  @Override
  public ResponseEntity<TaskCombinedSchemaTO> getTaskSchema(String taskId) {
    val taskWithSchema = workOnUserTask.loadUserTaskWithSchema(taskId);
    return ok(taskMapper.to(taskWithSchema.getSchema()));
  }

  @Override
  public ResponseEntity<TaskWithSchemaTO> getTaskWithSchemaByTaskId(String taskId) {
    val taskWithSchema = workOnUserTask.loadUserTaskWithSchema(taskId);
    switch (taskWithSchema.getTaskSchemaType()) {
      case VUETIFY_FORM_BASE:
        return ok(taskMapper.toWithSchema(taskWithSchema.getTask(), taskWithSchema.getLegacyForm(), taskWithSchema.isCancelable(), taskWithSchema.getTaskSchemaType(), taskWithSchema.getTag(), taskWithSchema.getTaskLinks()));
      case SCHEMA_BASED:
      default:
        return ok(taskMapper.toWithSchema(taskWithSchema.getTask(), taskWithSchema.getSchema().asMap(), taskWithSchema.isCancelable(), taskWithSchema.getTaskSchemaType(), taskWithSchema.getTag(), taskWithSchema.getTaskLinks()));
    }
  }

  @Override
  public ResponseEntity<TaskWithDetailsTO> getTaskByTaskId(String taskId) {
    val taskWithSchemaRef = workOnUserTask.loadUserTask(taskId);
    return ok(taskMapper.toWithDetails(taskWithSchemaRef.getTask(), taskWithSchemaRef.getSchemaRef(), taskWithSchemaRef.isCancelable(), taskWithSchemaRef.getTaskSchemaType(), taskWithSchemaRef.getTag(), taskWithSchemaRef.getTaskLinks()));
  }

  @Override
  public ResponseEntity<Void> completeTask(String taskId, VariableMapTO variableMapTO) {
    workOnUserTask.completeUserTask(taskId, variableMapTO.getVariables());
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> saveTaskVariables(String taskId, VariableMapTO variableMapTO) {
    workOnUserTask.saveUserTask(taskId, variableMapTO.getVariables());
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> assignTask(String taskId, TaskAssignmentTO taskAssignmentTO) {
    workOnUserTask.assignUserTask(taskId, taskAssignmentTO.getAssignee());
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> unassignTask(String taskId) {
    workOnUserTask.unassignUserTask(taskId);
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> deferTask(String taskId, TaskDeferralTO taskDeferralTO) {
    workOnUserTask.deferUserTask(taskId, taskDeferralTO.getFollowUpDate());
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> undeferTask(String taskId) {
    workOnUserTask.undeferUserTask(taskId);
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> cancelTask(String taskId) {
    workOnUserTask.cancelUserTask(taskId);
    return noContent().build();
  }
}
