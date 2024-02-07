package de.muenchen.oss.digiwf.task.service.adapter.in.rest.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.task.TaskExternalReference;
import de.muenchen.oss.digiwf.task.TaskSchemaType;
import de.muenchen.oss.digiwf.task.service.application.port.in.rest.model.*;
import de.muenchen.oss.digiwf.task.service.domain.JsonSchema;
import de.muenchen.oss.digiwf.task.service.domain.PageOfTasksWithSchema;
import de.muenchen.oss.digiwf.task.service.domain.TaskLink;
import de.muenchen.oss.digiwf.task.service.domain.legacy.Form;
import io.holunda.polyflow.view.Task;
import de.muenchen.oss.digiwf.task.service.application.port.in.rest.model.*;
import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface TaskMapper {

  /**
   * Mapper for Task TO, used in list operations.
   * @param task task from polyflow.
   * @param schemaRef schema reference.
   * @return Task TO.
   */
  @Mapping(target = "processName", source = "task.sourceReference.name")
  @Mapping(target = "schemaRef", source = "schemaRef")
  @Mapping(target = "schemaType", source = "schemaType")
  @Mapping(target = "tag", source = "tag")
  @Mapping(target = "externalLinks", source = "externalLinks")
  TaskTO to(Task task, String schemaRef, @NonNull TaskSchemaType schemaType, String tag, @NonNull List<TaskLink> externalLinks);

  @Mapping(target = "processName", source = "task.sourceReference.name")
  @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
  @Mapping(target = "variables", source = "task.payload")
  @Mapping(target = "schemaRef", source = "schemaRef")
  @Mapping(target = "cancelable", source = "cancelable")
  @Mapping(target = "schemaType", source = "schemaType")
  @Mapping(target = "tag", source = "tag")
  @Mapping(target = "externalLinks", source = "externalLinks")
  TaskWithDetailsTO toWithDetails(Task task, String schemaRef, Boolean cancelable, @NonNull TaskSchemaType schemaType, String tag, @NonNull List<TaskLink> externalLinks);

  @Mapping(target = "schemaId", source = "id")
  @Mapping(target = "schemaJson", source = "schema")
  TaskCombinedSchemaTO to(JsonSchema schema);


  @Mapping(target = "id", source = "task.id")
  @Mapping(target = "processName", source = "task.sourceReference.name")
  @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
  @Mapping(target = "variables", source = "task.payload")
  @Mapping(target = "createTime", source = "task.createTime")
  @Mapping(target = "description", source = "task.description")
  @Mapping(target = "name", source = "task.name")
  @Mapping(target = "assignee", source = "task.assignee")
  @Mapping(target = "followUpDate", source = "task.followUpDate")
  @Mapping(target = "schema", source = "schema")
  @Mapping(target = "cancelable", source = "cancelable")
  @Mapping(target = "schemaType", source = "schemaType")
  @Mapping(target = "tag", source = "tag")
  @Mapping(target = "externalLinks", source = "externalLinks")
  TaskWithSchemaTO toWithSchema(@Nonnull Task task, @Nonnull Map<String, Object> schema, @NonNull Boolean cancelable, @NonNull TaskSchemaType schemaType, String tag, @NonNull List<TaskLink> externalLinks);


  @Mapping(target = "id", source = "task.id")
  @Mapping(target = "processName", source = "task.sourceReference.name")
  @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
  @Mapping(target = "variables", source = "task.payload")
  @Mapping(target = "createTime", source = "task.createTime")
  @Mapping(target = "description", source = "task.description")
  @Mapping(target = "name", source = "task.name")
  @Mapping(target = "assignee", source = "task.assignee")
  @Mapping(target = "followUpDate", source = "task.followUpDate")
  @Mapping(target = "schema", source = "form")
  @Mapping(target = "cancelable", source = "cancelable")
  @Mapping(target = "schemaType", source = "schemaType")
  @Mapping(target = "tag", source = "tag")
  @Mapping(target = "externalLinks", source = "externalLinks")
  TaskWithSchemaTO toWithSchema(@Nonnull Task task, @Nonnull Form form, @NonNull Boolean cancelable, @NonNull TaskSchemaType schemaType, String tag, @NonNull List<TaskLink> externalLinks);

  default Map<String,Object> map(Form value) {
    val objectMapper = new ObjectMapper();
    val mapType = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class);
    return objectMapper.convertValue(value, mapType);
  }

  default TaskSchemaTypeTO toSchemaTO(TaskSchemaType taskSchemaType) {
    switch (taskSchemaType) {
      case SCHEMA_BASED:
        return TaskSchemaTypeTO.SCHEMA_BASED;
      case VUETIFY_FORM_BASE:
        return TaskSchemaTypeTO.VUETIFY_FORM_BASE;
      default:
        return null;
    }
  }

  default PageOfTasksTO toSchemaTO(PageOfTasksWithSchema domain) {
    var pagingAndSorting = domain.getPagingAndSorting();
    var totalPages = Double.valueOf(Math.ceil(domain.getTotalElementsCount() / pagingAndSorting.getPageSize().doubleValue())).intValue();
    var tasks = domain.getTasks().stream().map(taskWithSchema -> this.to(
        taskWithSchema.getTask(),
        taskWithSchema.getSchemaRef(),
        taskWithSchema.getTaskSchemaType(),
        taskWithSchema.getTag(),
        taskWithSchema.getTaskLinks()
        )).collect(Collectors.toList());
    var empty = domain.getTotalElementsCount() == 0;
    var sortRequested = pagingAndSorting.getSort() != null;
    return new PageOfTasksTO()
        .pageable(new PageOfTasksPageableTO()
            .paged(true)
            .unpaged(false)
            .pageNumber(pagingAndSorting.getPageIndex())
            .pageSize(pagingAndSorting.getPageSize())
            .sort(new PageOfTasksPageableSortTO()
                .sorted(sortRequested)
                .unsorted(!sortRequested)
                .empty(empty)
            )
        )
        .page(pagingAndSorting.getPageIndex())
        .content(tasks)
        .size(pagingAndSorting.getPageSize())
        .numberOfElements(tasks.size())
        .totalPages(totalPages)
        .totalElements(domain.getTotalElementsCount())
        .empty(empty)
        .first(pagingAndSorting.getPageIndex() == 0)
        .last(pagingAndSorting.getPageIndex() == totalPages - 1);
  }

  default ExternalLinkTO externalLink(TaskLink link) {
    return new ExternalLinkTO()
        .url(link.getUrl())
        .type(link.getType())
        .htmlContent(link.getHtmlContent())
        .label(link.getLabel())
        .additionalParameters(link.getAdditionalParameters());
  }
}
