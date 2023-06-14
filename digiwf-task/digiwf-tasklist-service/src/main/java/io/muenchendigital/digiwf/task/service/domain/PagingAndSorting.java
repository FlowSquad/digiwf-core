package io.muenchendigital.digiwf.task.service.domain;

import lombok.Data;

/**
 * Encapsulation of paging and sorting information.
 */
@Data
public class PagingAndSorting {
  private final Integer pageIndex;
  private final Integer pageSize;
  private final String sort;

  /**
   * Default sort column.
   */
  public static final String DEFAULT_SORT = "-createdTime";

  public String getSanitizedSort() {
    if (sort == null) {
      return DEFAULT_SORT;
    } else {
      if (sort.charAt(0) != '+' && sort.charAt(0) != '-') {
        throw new IllegalArgumentException("Sort argument must start with '+' for ascending or '-' for descending");
      }
      return sort;
    }
  }
}
