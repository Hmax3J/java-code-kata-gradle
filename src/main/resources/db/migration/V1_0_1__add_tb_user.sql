CREATE TABLE IF NOT EXISTS "user" (
    username                VARCHAR(255),
    password                VARCHAR(255),
    nickname                VARCHAR(255),

    status                  VARCHAR(255),
    created_at              TIMESTAMP               DEFAULT now(),
    updated_at              TIMESTAMP,

    CONSTRAINT pk_user PRIMARY KEY (username),
    CONSTRAINT uq_user_nickname UNIQUE (nickname)
);

COMMENT ON TABLE "user" IS '회원';
COMMENT ON COLUMN "user".username IS '사용자 ID';
COMMENT ON COLUMN "user".password IS '비밀번호';
COMMENT ON COLUMN "user".nickname IS '닉네임';
COMMENT ON COLUMN "user".status IS '계정 상태';
COMMENT ON COLUMN "user".created_at IS '생성일(가입일)';
COMMENT ON COLUMN "user".updated_at IS '최종 수정일';