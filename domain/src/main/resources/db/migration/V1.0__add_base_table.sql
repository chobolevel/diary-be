create table scrimmers.revisions
(
  revision_id        bigint auto_increment
        primary key,
  revision_timestamp bigint                             not null,
  created_at         datetime default CURRENT_TIMESTAMP not null
) comment '개정 테이블';

