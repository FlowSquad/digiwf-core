package io.muenchendigital.digiwf.task;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import java.util.List;

import static io.holunda.camunda.bpm.data.CamundaBpmData.*;

/**
 * Definition of task variables.
 */
public class TaskVariables {
  /**
   * Task variable containing the JSON schema for the task.
   */
  public static final VariableFactory<String> TASK_SCHEMA_KEY = stringVariable("app_task_schema_key");

  /**
   * Task variable containing the assignee of the task.
   */
  public static final VariableFactory<String> TASK_ASSIGNEE = stringVariable("app_task_assignee");
  /**
   * Task variable containing the candidate users of the task.
   */
  public static final VariableFactory<List<String>> TASK_CANDIDATE_USERS = listVariable("app_task_candidate_users", String.class);
  /**
   * Task variable containing the candidate groups of the task.
   */
  public static final VariableFactory<List<String>> TASK_CANDIDATE_GROUPS = listVariable("app_task_candidate_groups", String.class);

  /**
   * Flag indicating if the task is cancellable.
   */
  public static final VariableFactory<Boolean> TASK_CANCELABLE = booleanVariable("app_task_cancelable");

  /**
   * Reflects the type of the schema used in task.
   */
  public static final VariableFactory<TaskSchemaType> TASK_SCHEMA_TYPE = customVariable("app_task_schema_type", TaskSchemaType.class);

  /**
   * Reflects the task description.
   */
  public static final VariableFactory<String> TASK_DESCRIPTION = stringVariable("app_task_description");

  /**
   * Reflects the task description.
   */
  public static final VariableFactory<String> TASK_DESCRIPTION_LEGACY = stringVariable("digitalwf_task_description");

}
