create table daangn.revisions
(
  revision_id        bigint auto_increment
        primary key,
  revision_timestamp bigint not null
) comment '변경 이력 관리 테이블';
