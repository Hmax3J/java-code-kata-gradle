package com.exampl.kata.common.support.paging;

public sealed interface Pageable
        permits OffsetPagingParameter,
        KeySetPagingParameter {
    int size();
}
