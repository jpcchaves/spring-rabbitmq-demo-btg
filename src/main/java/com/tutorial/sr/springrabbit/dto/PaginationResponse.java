package com.tutorial.sr.springrabbit.dto;

import org.springframework.data.domain.Page;

public record PaginationResponse(
    Integer page, Integer pageSize, Long totalElements, Integer totalPages) {

  public static PaginationResponse paginationResponseFromPage(Page<?> page) {

    return new PaginationResponse(
        page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
  }
}
