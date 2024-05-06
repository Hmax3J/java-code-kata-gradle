package com.exampl.kata.app.order.mapper;

import com.exampl.kata.app.order.domain.Order;
import com.exampl.kata.app.order.domain.OrderStatus;
import com.exampl.kata.common.support.jdbc.NullableJdbcMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
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
            OrderStatus status = jdbcMapper.getEnum("status", OrderStatus.class);

            Instant createdAt = jdbcMapper.getInstant("created_at");
            Instant updatedAt = jdbcMapper.getInstant("updated_at");

            return Order.builder()
                    .id(id)
                    .fishbreadId(fishbreadId)
                    .fishbreadName(fishbreadName)
                    .unitPrice(unitPrice)
                    .count(count)
                    .totalPrice(totalPrice)
                    .status(status)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> toEntityList(ResultSet resultSet) {
        // 실제론 성능 비교해 봐야 알지만. (메모리 낱개 할당 비용 vs 이사 비용)
        List<Order> list = new LinkedList<>();

        try {
            while (!(resultSet.isLast() || resultSet.isAfterLast())) {
                Order order = toEntity(resultSet);
                list.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // LinkedList의 toArray()는 잘 구현돼 있음
        // copyOf는 아마 LinkedList의 toArray를 사용할 것으로 보임.
        return List.copyOf(list);
    }
}
