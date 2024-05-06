package com.exampl.kata.app.order.dao;

import com.exampl.kata.app.order.domain.Order;
import com.exampl.kata.app.order.domain.OrderStatus;
import com.exampl.kata.app.order.mapper.OrderJdbcMapper;
import com.exampl.kata.common.support.jdbc.NullableFieldJdbcStatement;
import com.exampl.kata.common.support.paging.*;
import com.exampl.kata.common.utils.jdbc.postgres.PostgresqlColumnTypeUtil;

import java.sql.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.exampl.kata.common.support.db.DatabaseConstants.*;

public class OrderDao {
    static {
        try {
            Class.forName(DB_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<List<OrderStatus>, String> map = new ConcurrentHashMap<>();

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
            jdbcStatement.setString(index++, order.status.name());
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
    
    public List<Order> findByStatus(OrderStatus status, Pageable pageable) {
        Objects.requireNonNull(status);

        String sql = """
                SELECT  *
                FROM    "order"
                WHERE   status = ?
                """;

        String suffixSql = switch (pageable) { // JDK 20
            case OffsetPagingParameter param -> {
                int size = param.size();
                int page = param.page();

                yield MessageFormat.format("""
                        LIMIT {0} OFFSET {1}
                        """,
                        size,
                        page * size
                );
            }
            case KeySetPagingParameter param -> {
                int size = param.size();
                String columnName = param.columnName();
                Object value = param.value();
                ColumnType columnType = param.type();
                OrderDirection direction = param.direction();

                yield MessageFormat.format("""
                            AND {0} {1} {2}
                        ORDER BY {0}
                        LIMIT {3}
                        """,
                        columnName,
                        direction.exclusiveSign(),
                        PostgresqlColumnTypeUtil.toSqlValue(value, columnType),
                        size
                );
            }
            case null -> throw new Error();
        };

        // TODO 페이징
        return Collections.emptyList();
    }

    public List<Order> findByStatusIn(List<OrderStatus> statusList) {
        assert !(statusList == null || statusList.isEmpty()) : "";

        // String 상수 풀
        String statusIn = map.computeIfAbsent(statusList, this::generateStatusIn);
        String sql = MessageFormat.format(
                "SELECT * FROM \"order\" WHERE {0}",
                statusIn
        );

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL,
                        DB_USERNAME,
                        DB_PASSWORD
                );
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(sql);
            return mapper.toEntityList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // TODO 페이징
        // return Collections.emptyList();
    }

    private String generateStatusIn(List<OrderStatus> statusList) {
        if (statusList == null || statusList.isEmpty()) {
            throw new RuntimeException("status 목록을 비우고 조회할 수 없습니다.");
        }

        List<String> strList = statusList.stream()
                .map(OrderStatus::name) // 따로 오버라이딩 안 했으면 Enum::name와 같음.
                .toList();

        return MessageFormat.format(
                "status IN ('{0}')",
                String.join("', '", strList)
        );
    }
}
