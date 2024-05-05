CREATE TABLE IF NOT EXISTS "order" (
    id                      UUID                    DEFAULT uuid_generate_v4(),
    fishbread_id            BIGINT,
    fishbread_name          VARCHAR(255),
    unit_price              INTEGER,
    count                   INTEGER,
    total_price             INTEGER,

    status                  VARCHAR(255),
    created_at              TIMESTAMP               DEFAULT now(),
    updated_at              TIMESTAMP,

    CONSTRAINT pk_order PRIMARY KEY (id)
);

COMMENT ON TABLE "order" IS '주문';

COMMENT ON COLUMN "order".id IS 'PK (UUID)';
COMMENT ON COLUMN "order".fishbread_id IS 'fishbread 테이블의 ID';
COMMENT ON COLUMN "order".fishbread_name IS 'fishbread 상품 이름';
COMMENT ON COLUMN "order".unit_price IS '단가';
COMMENT ON COLUMN "order".count IS '주문 수량';
COMMENT ON COLUMN "order".total_price IS '총액';

COMMENT ON COLUMN "order".status IS '계정 상태';
COMMENT ON COLUMN "order".created_at IS '생성일';
COMMENT ON COLUMN "order".updated_at IS '최종 수정일';