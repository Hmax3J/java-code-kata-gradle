package com.exampl.kata.common.support.paging;

public record OffsetPagingParameter(int size, int page) implements Pageable {

}
