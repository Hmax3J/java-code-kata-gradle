package com.exampl.kata.common.support.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class NullableJdbcMapper {

    private final ResultSet resultSet; // delegation 위임 (Proxy이기도 함.)

    public NullableJdbcMapper(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public UUID getUuid(String columnName) throws SQLException {
        String stringValue = resultSet.getString(columnName);
        return stringValue != null
                ? UUID.fromString(stringValue)
                : null;
    }

    public String getString(String columnName) throws SQLException {
        return resultSet.getString(columnName);
    }

    public Integer getInt(String columnName) throws SQLException {
        String stringValue = resultSet.getString(columnName);
        return stringValue != null
                ? Integer.parseInt(stringValue)
                : null;
    }

    public Long getLong(String columnName) throws SQLException {
        String stringValue = resultSet.getString(columnName);
        return stringValue != null
                ? Long.parseLong(stringValue)
                : null;
    }

    public Instant getInstant(String columnName) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnName);
        return timestamp != null
                ? timestamp.toInstant()
                : null;
    }

    public <T extends Enum> T getEnum(String columnName, Class<T> clazz) throws SQLException {
        String stringValue = resultSet.getString(columnName);
        return stringValue != null
                ? (T) T.valueOf(clazz, stringValue)
                : null;
    }
}
