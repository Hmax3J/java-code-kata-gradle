package com.exampl.kata.common.utils.jdbc.postgres;

import com.exampl.kata.common.support.paging.ColumnType;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;

public final class PostgresqlColumnTypeUtil {

    public static String toSqlValue(Object value, ColumnType type) {
        return switch (type) {
            case INTEGER, BIGINT -> String.valueOf(value);
            case TIMESTAMP -> toTimestamp(value).toString();
            case VARCHAR -> MessageFormat.format("'{0}'", value.toString());
            case null -> throw new Error();
        };
    }

    private PostgresqlColumnTypeUtil() {}

    private static Timestamp toTimestamp(Object value) {
        return switch (value) {
            case LocalDateTime time -> Timestamp.valueOf(time);
            case String time -> Timestamp.valueOf(time);
            case Instant time -> Timestamp.from(time);
            default -> Timestamp.valueOf(value.toString());
        };
    }
}
