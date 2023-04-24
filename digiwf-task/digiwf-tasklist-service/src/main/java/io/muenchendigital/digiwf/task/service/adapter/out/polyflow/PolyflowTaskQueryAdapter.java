package io.muenchendigital.digiwf.task.service.adapter.out.polyflow;

import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.TaskQueryClient;
import io.holunda.polyflow.view.auth.User;
import io.holunda.polyflow.view.query.task.TaskForIdQuery;
import io.holunda.polyflow.view.query.task.TasksForGroupQuery;
import io.holunda.polyflow.view.query.task.TasksForUserQuery;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.domain.PageOfTasks;
import io.muenchendigital.digiwf.task.service.domain.PagingAndSorting;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PolyflowTaskQueryAdapter implements TaskQueryPort {

  private final TaskQueryClient taskQueryClient;

  @Override
  public PageOfTasks getTasksForCurrentUser(User currentUser, String query, LocalDate followUp, PagingAndSorting pagingAndSorting) {
    var filters = buildFilters(query, followUp);
    var result = taskQueryClient.query(new TasksForUserQuery(
        new User(currentUser.getUsername(), Collections.emptySet()), // no groups in user-based query
        pagingAndSorting.getPageIndex(),
        pagingAndSorting.getPageSize(),
        pagingAndSorting.getSanitizedSort(),
        filters
    )).join();
    return new PageOfTasks(
        result.getElements(),
        result.getTotalElementCount(),
        pagingAndSorting
    );
  }

  @Override
  public PageOfTasks getTasksForCurrentUserGroup(User currentUser, String query, boolean includeAssigned, PagingAndSorting pagingAndSorting) {
    var filters = buildFilters(query, null);
    var result = taskQueryClient.query(new TasksForGroupQuery(
        currentUser,
        includeAssigned,
        pagingAndSorting.getPageIndex(),
        pagingAndSorting.getPageSize(),
        pagingAndSorting.getSanitizedSort(),
        filters
    )).join();
    return new PageOfTasks(
        result.getElements(),
        result.getTotalElementCount(),
        pagingAndSorting
    );
  }

  @Override
  public Task getTaskByIdForCurrentUser(User currentUser, String taskId) {
    /*
     * Think about the implementation and authorization here.
     * Possibilities are:
     * - query for all tasks for user filtering for id
     * - get task by id and check the authorization afterwards
     * Currently taking the second option.
     */
    return taskQueryClient.query(
            new TaskForIdQuery(taskId)
        ).join()
        .filter(task -> isAllowedForCurrentUser(task, currentUser))
        .orElseThrow(() -> new TaskNotFoundException(taskId));
  }

  private List<String> buildFilters(String query, LocalDate followUp) {
    val filters = new ArrayList<String>();
    if (followUp != null) {
      filters.add("task.followUpDate<" + followUp.atStartOfDay().plusSeconds(1).toInstant(ZoneOffset.UTC));
    }
    if (query != null && !query.isEmpty()) {
      filters.add("task.textSearch%" + query);
    }
    return filters;
  }

  private boolean isAllowedForCurrentUser(Task task, User currentUser) {
    Objects.requireNonNull(task);
    var assignedToCurrentUser = task.getAssignee() != null && task.getAssignee().equals(currentUser.getUsername());
    var matchesUsersGroup = task.getCandidateGroups().stream().anyMatch(group -> currentUser.getGroups().contains(group));
    return assignedToCurrentUser || matchesUsersGroup;
  }
}
