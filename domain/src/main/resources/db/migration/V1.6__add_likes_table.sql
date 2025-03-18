create table daangn.likes
(
  id         varchar(13) not null comment '아이디'
        primary key,
  user_id    varchar(13) not null comment '회원 아이디',
  type       varchar(20) not null comment '좋아요 유형',
  target_id  varchar(13) not null comment '좋아요 대상 아이디',
  deleted    bit         not null comment '삭제 여부',
  created_at datetime    not null comment '생성 일자',
  updated_at datetime    not null comment '수정 일자'
);

create index likes_user_id_index
  on daangn.likes (user_id);

create table daangn.likes_histories
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  user_id       varchar(13) not null,
  type          varchar(20) not null,
  target_id     varchar(13) not null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
)
  comment '좋아요 이력 테이블';

