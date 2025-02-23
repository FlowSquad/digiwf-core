package de.muenchen.oss.digiwf.task.service.adapter.out.engine;

import de.muenchen.oss.digiwf.task.BpmnErrors;
import de.muenchen.oss.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import lombok.val;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

/**
 * Encapsulation of the Camunda remote client.
 */
@Component
public class RemoteTaskCommandRestAdapter implements TaskCommandPort {
  private final TaskService taskService;

  public RemoteTaskCommandRestAdapter(@Qualifier("remote") TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void completeUserTask(String taskId, Map<String, Object> variables) {
    taskService.complete(taskId, variables);
  }

  @Override
  public void saveUserTask(String taskId, Map<String, Object> payload) {
    // setVariables does not trigger update event
    taskService.setVariables(taskId, payload);
    // fetch task and save it to trigger update event
    val task = taskService.createTaskQuery().taskId(taskId).singleResult();
    taskService.saveTask(task);
  }

  @Override
  public void assignUserTask(String taskId, String assignee) {
    taskService.setAssignee(taskId, assignee);
  }

  @Override
  public void unassignUserTask(String taskId) {
    taskService.setAssignee(taskId, null);
  }

  @Override
  public void deferUserTask(String taskId, Instant followUpDate) {
    var task = taskService.createTaskQuery().taskId(taskId).singleResult();
    if (task != null) {
      task.setFollowUpDate(Date.from(followUpDate));
      taskService.saveTask(task);
    }
  }

  @Override
  public void undeferUserTask(String taskId) {
    var task = taskService.createTaskQuery().taskId(taskId).singleResult();
    if (task != null) {
      task.setFollowUpDate(null);
      taskService.saveTask(task);
    }
  }

  @Override
  public void cancelUserTask(String taskId) {
    taskService.handleBpmnError(taskId, BpmnErrors.DEFAULT_TASK_CANCELLATION_ERROR);
  }
}
