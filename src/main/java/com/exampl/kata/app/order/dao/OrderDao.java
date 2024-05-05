package com.exampl.kata.app.order.dao;

import com.exampl.kata.app.order.domain.Order;
import com.exampl.kata.app.order.mapper.OrderJdbcMapper;
import com.exampl.kata.common.support.jdbc.NullableFieldJdbcStatement;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.exampl.kata.common.support.db.DatabaseConstants.*;

public class OrderDao {
    static {
        try {
            Class.forName(DB_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final OrderJdbcMapper mapper = new OrderJdbcMapper();

    public int insert(Order order) {
        String sql = """
                INSERT INTO "order" (
                    fishbread_id,
                    fishbread_name,
                    unit_price,
                    count,
                    total_price,
                    status,
                    created_at,
                    updated_at
                ) VALUES (
                    ?, -- fishbread_id
                    ?, -- fishbread_name
                    ?, -- unit_price
                    ?, -- count
                    ?, -- total_price
                    ?, -- status
                    ?, -- created_at
                    ?  -- updated_at
                )""";

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            NullableFieldJdbcStatement jdbcStatement = new NullableFieldJdbcStatement(statement);

            int index = 1;
            jdbcStatement.setLong(index++, order.fishbreadId);
            jdbcStatement.setString(index++, order.fishbreadName);
            jdbcStatement.setInt(index++, order.unitPrice);
            jdbcStatement.setInt(index++, order.count);
            jdbcStatement.setInt(index++, order.totalPrice);
            jdbcStatement.setString(index++, order.status);
            jdbcStatement.setTimestamp(index++, order.createdAt);
            jdbcStatement.setTimestamp(index, order.updatedAt);

            return jdbcStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Order> findById(UUID id) {
        String sql = """
                SELECT  *
                FROM    "order"
                WHERE   id = ?""";

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                NullableFieldJdbcStatement statement = new NullableFieldJdbcStatement(preparedStatement)
        ) {
            int index = 1;
            statement.setUuid(index, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            
            Order order = mapper.toEntity(resultSet);
            return Optional.of(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Order> findByStatus() {
        // TODO 상태에 따라 반환, 페이징
        return Collections.emptyList();
    }
}
