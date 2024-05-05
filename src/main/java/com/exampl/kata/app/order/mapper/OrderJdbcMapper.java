package com.exampl.kata.app.order.mapper;

import com.exampl.kata.app.order.domain.Order;
import com.exampl.kata.common.support.jdbc.NullableJdbcMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class OrderJdbcMapper {
    public Order toEntity(ResultSet resultSet) {
        try {
            resultSet.next();
            NullableJdbcMapper jdbcMapper = new NullableJdbcMapper(resultSet);
            UUID id = jdbcMapper.getUuid("id");
            Long fishbreadId = jdbcMapper.getLong("fishbread_id");
            String fishbreadName = jdbcMapper.getString("fishbread_name");
            Integer unitPrice = jdbcMapper.getInt("unit_price");
            Integer count = jdbcMapper.getInt("count");
            Integer totalPrice = jdbcMapper.getInt("total_price");
            String statusString = jdbcMapper.getString("status");

            Instant createdAt = jdbcMapper.getInstant("created_at");
            Instant updatedAt = jdbcMapper.getInstant("updated_at");

            return Order.builder()
                    .id(id)
                    .fishbreadId(fishbreadId)
                    .fishbreadName(fishbreadName)
                    .unitPrice(unitPrice)
                    .count(count)
                    .totalPrice(totalPrice)
                    .status(statusString)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
