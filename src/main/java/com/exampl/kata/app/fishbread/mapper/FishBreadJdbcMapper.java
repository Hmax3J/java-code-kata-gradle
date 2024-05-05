package com.exampl.kata.app.fishbread.mapper;

import com.exampl.kata.app.fishbread.domain.FishBread;
import com.exampl.kata.app.fishbread.domain.FishBreadStatus;
import com.exampl.kata.common.support.jdbc.NullableJdbcMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FishBreadJdbcMapper {
    public FishBread toEntity(ResultSet resultSet) {
        try {
            resultSet.next();
            NullableJdbcMapper jdbcMapper = new NullableJdbcMapper(resultSet);
            Long id = jdbcMapper.getLong("id");
            String name = jdbcMapper.getString("name");
            String type = jdbcMapper.getString("type");
            Integer unitPrice = jdbcMapper.getInt("unit_price");
            Integer stock = jdbcMapper.getInt("stock");
            FishBreadStatus status = jdbcMapper.getEnum("status", FishBreadStatus.class);
            Instant createdAt = jdbcMapper.getInstant("created_at");
            Instant updatedAt = jdbcMapper.getInstant("updated_at");

            return FishBread.builder()
                    .id(id)
                    .name(name)
                    .type(type)
                    .unitPrice(unitPrice)
                    .stock(stock)
                    .status(status)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FishBread> toEntityList(ResultSet resultSet) {
        // 실제론 성능 비교해 봐야 알지만. (메모리 낱개 할당 비용 vs 이사 비용)
        List<FishBread> list = new LinkedList<>();

        try {
            while (!(resultSet.isLast() || resultSet.isAfterLast())) {
                FishBread fishBread = toEntity(resultSet);
                list.add(fishBread);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // LinkedList의 toArray()는 잘 구현돼 있음
        // copyOf는 아마 LinkedList의 toArray를 사용할 것으로 보임.
        return List.copyOf(list);
    }
}
