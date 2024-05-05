CREATE TABLE IF NOT EXISTS fishbread (
    id                      BIGSERIAL,
    name                    VARCHAR(255),
    type                    VARCHAR(255),
    unit_price              INTEGER,
    stock                   INTEGER,

    status                  VARCHAR(255),
    created_at              TIMESTAMP               DEFAULT now(),
    updated_at              TIMESTAMP,

    CONSTRAINT pk_fishbread PRIMARY KEY (id),
    CONSTRAINT uq_fishbread_type UNIQUE (type)
);

COMMENT ON TABLE fishbread IS '붕어빵';

COMMENT ON COLUMN fishbread.id IS 'PK (auto increment)';

COMMENT ON COLUMN fishbread.name IS '이름(전시할 메뉴 이름)';
COMMENT ON COLUMN fishbread.type IS '붕어빵 분류';
COMMENT ON COLUMN fishbread.unit_price IS '단가';
COMMENT ON COLUMN fishbread.stock IS '재고';

COMMENT ON COLUMN fishbread.status IS '상태';
COMMENT ON COLUMN fishbread.created_at IS '생성일';
COMMENT ON COLUMN fishbread.updated_at IS '최종 수정일';