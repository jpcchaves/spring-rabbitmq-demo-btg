package com.tutorial.sr.springrabbit.controller;

import com.tutorial.sr.springrabbit.dto.ApiResponse;
import com.tutorial.sr.springrabbit.dto.OrderResponse;
import com.tutorial.sr.springrabbit.dto.PaginationResponse;
import com.tutorial.sr.springrabbit.service.OrderService;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/customers/{customerId}/orders")
  public ResponseEntity<ApiResponse<OrderResponse>> listOrders(
      @PathVariable(name = "customerId") Long customerId,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

    Page<OrderResponse> pageResponse =
        orderService.listAll(customerId, PageRequest.of(page, pageSize));

    return ResponseEntity.ok(
        new ApiResponse<>(
            Map.of("totalOnOrders", orderService.findTotalOnOrdersByCustomerId(customerId)),
            pageResponse.getContent(),
            PaginationResponse.paginationResponseFromPage(pageResponse)));
  }
}
