package com.tutorial.sr.springrabbit.dto;

import java.util.List;

public class OrderCreatedEvent {
    private Long codigoPedido;
    private Long codigoCliente;
    private List<OrderItemEvent> items;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long codigoPedido, Long codigoCliente, List<OrderItemEvent> items) {
        this.codigoPedido = codigoPedido;
        this.codigoCliente = codigoCliente;
        this.items = items;
    }

    public Long getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(Long codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Long getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Long codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public List<OrderItemEvent> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEvent> items) {
        this.items = items;
    }
}
