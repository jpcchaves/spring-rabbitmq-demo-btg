package com.tutorial.sr.springrabbit.service;

import com.tutorial.sr.springrabbit.dto.OrderCreatedEvent;
import com.tutorial.sr.springrabbit.entity.OrderEntity;
import com.tutorial.sr.springrabbit.entity.OrderItemEntity;
import com.tutorial.sr.springrabbit.repository.OrderRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public void save(OrderCreatedEvent event) {

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setOrderId(event.getCodigoCliente());
    orderEntity.setCustomerId(event.getCodigoCliente());

    orderEntity.setItems(
        event.getItems().stream()
            .map(
                item ->
                    new OrderItemEntity(item.getProduto(), item.getQuantidade(), item.getPreco()))
            .toList());

    orderEntity.setTotal(getTotal(event));

    orderRepository.save(orderEntity);
  }

  private BigDecimal getTotal(OrderCreatedEvent event) {
    return event.getItems().stream()
        .map(item -> item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }
}
