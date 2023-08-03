package de.muenchen.oss.digiwf.task.service.application.usecase;

import de.muenchen.oss.digiwf.task.service.application.port.in.RetrieveTasksForUser;
import de.muenchen.oss.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.cancellation.CancellationFlagOutPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.TaskSchemaTypeResolverPort;
import de.muenchen.oss.digiwf.task.service.domain.PageOfTasks;
import de.muenchen.oss.digiwf.task.service.domain.PageOfTasksWithSchema;
import de.muenchen.oss.digiwf.task.service.domain.PagingAndSorting;
import de.muenchen.oss.digiwf.task.service.domain.TaskWithSchemaRef;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RetrieveTasksForUserUseCase implements RetrieveTasksForUser {

  private final TaskQueryPort taskQueryPort;
  private final CurrentUserPort currentUserPort;
  private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;
  private final TaskSchemaTypeResolverPort taskSchemaTypeResolverPort;
  private final CancellationFlagOutPort cancellationFlagOutPort;

  @Override
  public PageOfTasksWithSchema getUnassignedTasksForCurrentUserGroup(String query, PagingAndSorting pagingAndSorting) {
    var currentUser = currentUserPort.getCurrentUser();
    var result = taskQueryPort.getTasksForCurrentUserGroup(currentUser, query, false, pagingAndSorting);
    return enrichWithSchema(result);
  }

  @Override
  public PageOfTasksWithSchema getAssignedTasksForCurrentUserGroup(String query, PagingAndSorting pagingAndSorting) {
    var currentUser = currentUserPort.getCurrentUser();
    var result = taskQueryPort.getTasksForCurrentUserGroup(currentUser, query, true, pagingAndSorting);
    return enrichWithSchema(result);
  }

  @Override
  public PageOfTasksWithSchema getTasksForCurrentUser(String query, LocalDate followUp, PagingAndSorting pagingAndSorting) {
    var currentUser = currentUserPort.getCurrentUser();
    var result = taskQueryPort.getTasksForCurrentUser(currentUser, query, followUp, pagingAndSorting);
    return enrichWithSchema(result);
  }

  private PageOfTasksWithSchema enrichWithSchema(PageOfTasks result) {
    return new PageOfTasksWithSchema(
        result.getTasks().stream().map(task -> new TaskWithSchemaRef(
                task,
                taskSchemaRefResolverPort.apply(task),
                cancellationFlagOutPort.apply(task),
                taskSchemaTypeResolverPort.apply(task)
            )
        ).collect(Collectors.toList()),
        result.getTotalElementsCount(),
        result.getPagingAndSorting()
    );
  }
}
