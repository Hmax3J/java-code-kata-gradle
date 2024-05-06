package com.exampl.kata.app.order.domain;

public enum OrderStatus {
    /**
     * 접수 대기
     */
    PENDING,
    /**
     * 접수 완료
     */
    ON_PROGRESS,
    /**
     * 고객에 의한 취소
     */
    CANCELED,
    /**
     * 점주에 의한 취소
     */
    REJECTED,
    /**
     * 상품 준비됨.
     */
    PRODUCT_PREPARED,
    /**
     * 배송 중(전달 중)
     */
    ON_DELIVERY,
    /**
     * 전달 완료
     */
    DELIVERY_DONE
}
