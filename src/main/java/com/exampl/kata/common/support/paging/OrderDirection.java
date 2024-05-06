package com.exampl.kata.common.support.paging;

public enum OrderDirection {
    DESC("<=", "<"),
    ASC(">=", ">");

    private final String inclusiveSign;
    private final String exclusiveSign;

    OrderDirection(String inclusiveSign, String exclusiveSign) {
        this.inclusiveSign = inclusiveSign;
        this.exclusiveSign = exclusiveSign;
    }

    /**
     * target <= value
     * or
     * target >= value
     */
    public String inclusiveSign() {
        return inclusiveSign;
    }

    /**
     * target < value
     * or
     * target > value
     */
    public String exclusiveSign() {
        return exclusiveSign;
    }
}
