package com.exampl.kata.app.order.domain;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public class Order {
    public UUID id;
    public Long fishbreadId;
    public String fishbreadName;
    public Integer unitPrice;
    public Integer count;
    public Integer totalPrice;
    public OrderStatus status;
    public Instant createdAt;
    public Instant updatedAt;
}
