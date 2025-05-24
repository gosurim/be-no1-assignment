CREATE TABLE plan
(
    id       BIGINT       AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    name    VARCHAR(100) NOT NULL COMMENT '작성자명',
    pw    VARCHAR(100) NOT NULL COMMENT '비밀번호',
    date    VARCHAR(100) NOT NULL COMMENT '작성일',
    modi    VARCHAR(100) NOT NULL COMMENT '수정일',
    todo TEXT COMMENT '할일'
);
