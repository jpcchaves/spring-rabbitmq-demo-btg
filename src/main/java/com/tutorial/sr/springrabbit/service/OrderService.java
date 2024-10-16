package com.tutorial.sr.springrabbit.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.tutorial.sr.springrabbit.dto.OrderCreatedEvent;
import com.tutorial.sr.springrabbit.dto.OrderResponse;
import com.tutorial.sr.springrabbit.entity.OrderEntity;
import com.tutorial.sr.springrabbit.entity.OrderItemEntity;
import com.tutorial.sr.springrabbit.repository.OrderRepository;
import java.math.BigDecimal;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final MongoTemplate mongoTemplate;

  public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
    this.orderRepository = orderRepository;
    this.mongoTemplate = mongoTemplate;
  }

  public Page<OrderResponse> listAll(Long customerId, PageRequest pageRequest) {

    Page<OrderEntity> orders = orderRepository.findAllByCustomerId(customerId, pageRequest);

    return orders.map(OrderResponse::fromEntity);
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

  public BigDecimal findTotalOnOrdersByCustomerId(Long customerId) {

    Aggregation aggregations =
        newAggregation(
            match(Criteria.where("customerId").is(customerId)), group().sum("total").as("total"));

    AggregationResults<Document> response =
        mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);

    BigDecimal total =
        (BigDecimal) response.getUniqueMappedResult().getOrDefault("total", BigDecimal.ZERO);

    return total;
  }

  private BigDecimal getTotal(OrderCreatedEvent event) {
    return event.getItems().stream()
        .map(item -> item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }
}
