create table daangn.channels
(
  id         varchar(13)  not null comment '아이디'
        primary key,
  name       varchar(100) not null comment '채널명',
  deleted    bit          not null comment '삭제 여부',
  created_at datetime     not null comment '생성 일자',
  updated_at datetime     not null comment '수정 일자'
)
  comment '채널 테이블';

create table daangn.channels_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  name          varchar(100) not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
)
  comment '채널 이력 테이블';

