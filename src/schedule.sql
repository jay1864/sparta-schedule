CREATE TABLE `schedule`
(
    `id`         bigint NOT NULL AUTO_INCREMENT COMMENT '일정 ID',
    `schedule`   varchar(100) NOT NULL COMMENT '일정',
    `manager`    varchar(10) NOT NULL COMMENT '담당자명',
    `pw`         varchar(20) NOT NULL COMMENT '비밀번호',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4;

-- AUTO_INCREMENT 사용 시 DELETE 되면 일반적으로 그 다음 숫자가 기입됨.
-- 따라서 InnoDB 스토리지 엔진을 사용하여 서버가 재기동 되면 지워진 데이터의 숫자를 다시 사용하도록 한다.