package com.exampl.kata.common.support.jdbc;

import java.sql.*;
import java.time.Instant;
import java.util.UUID;

public class NullableFieldJdbcStatement implements AutoCloseable {

    // delegation (위임)
    private final PreparedStatement statement;

    public NullableFieldJdbcStatement(PreparedStatement statement) {
        this.statement = statement;
    }

    public void setLong(int parameterIndex, Long value) throws SQLException {
        if (setNullOrSkip(parameterIndex, value, Types.BIGINT)) {
            return;
        }
        statement.setLong(parameterIndex, value);
    }

    public void setString(int parameterIndex, String value) throws SQLException {
        if (setNullOrSkip(parameterIndex, value, Types.VARCHAR)) {
            return;
        }
        statement.setString(parameterIndex, value);
    }

    public void setInt(int parameterIndex, Integer value) throws SQLException {
        if (setNullOrSkip(parameterIndex, value, Types.INTEGER)) {
            return;
        }
        statement.setInt(parameterIndex, value);
    }

    public void setTimestamp(int parameterIndex, Instant value) throws SQLException {
        if (setNullOrSkip(parameterIndex, value, Types.TIMESTAMP)) {
            return;
        }
        Timestamp timestamp = Timestamp.from(value);
        statement.setTimestamp(parameterIndex, timestamp);
    }

    public void setTimestamp(int parameterIndex, Timestamp value) throws SQLException {
        if (setNullOrSkip(parameterIndex, value, Types.TIMESTAMP)) {
            return;
        }
        statement.setTimestamp(parameterIndex, value);
    }

    public void setUuid(int parameterIndex, UUID value) throws SQLException {
        if (setNullOrSkip(parameterIndex, value, Types.BINARY)) {
            return;
        }
        statement.setObject(parameterIndex, value);
    }

    public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        statement.close();
    }

    private boolean setNullOrSkip(int parameterIndex, Object value, int typesValue) throws SQLException {
        boolean isNull = value == null;
        if (isNull) {
            statement.setNull(parameterIndex, typesValue);
        }
        return isNull;
    }
}
