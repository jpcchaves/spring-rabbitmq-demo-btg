package com.tutorial.sr.springrabbit.dto;

public record PaginationResponse(
    Integer page, Integer pageSize, Integer totalElements, Integer totalPages) {}
