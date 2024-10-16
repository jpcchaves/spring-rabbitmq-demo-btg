package com.tutorial.sr.springrabbit.dto;

import java.math.BigDecimal;

public record OrderResponse(Long orderId, Long customerId, BigDecimal total) {}
