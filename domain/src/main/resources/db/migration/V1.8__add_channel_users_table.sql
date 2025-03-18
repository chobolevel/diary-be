create table daangn.channel_users
(
  id         varchar(13) not null comment '아이디'
        primary key,
  channel_id varchar(13) not null comment '채널 아이디',
  user_id    varchar(13) not null comment '회원 아이디',
  grade      varchar(20) not null comment '채널 회원 등급',
  deleted    bit         not null comment '삭제 여부',
  created_at datetime    not null comment '생성 일자',
  updated_at datetime    null comment '수정 일자'
)
  comment '채널 회원 테이블';

create index channel_users_channel_id_index
  on daangn.channel_users (channel_id);

create table daangn.channel_users_histories
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  channel_id    varchar(13) not null,
  user_id       varchar(13) not null,
  grade         varchar(20) not null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
)
  comment '채널 회원 이력 테이블';

