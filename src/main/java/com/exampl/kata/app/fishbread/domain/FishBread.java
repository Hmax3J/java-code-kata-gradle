package com.exampl.kata.app.fishbread.domain;

import lombok.Builder;

import java.time.Instant;

@Builder
public class FishBread {
    public Long id;
    public String name;
    public String type;
    public Integer unitPrice;
    public Integer stock;
    public FishBreadStatus status;
    public Instant createdAt;
    public Instant updatedAt;
}
