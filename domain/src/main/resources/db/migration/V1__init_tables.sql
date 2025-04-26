create table diary.revisions
(
  revision_id        bigint auto_increment comment '변경 이력 아이디'
        primary key,
  revision_timestamp bigint not null comment '변경 이력 타임스탬프'
)
  comment '변경 이력 테이블';

