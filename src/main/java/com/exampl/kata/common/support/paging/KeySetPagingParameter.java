package com.exampl.kata.common.support.paging;

public record KeySetPagingParameter (
        int size,
        String columnName,
        Object value,
        ColumnType type,
        OrderDirection direction
) implements Pageable {
}
