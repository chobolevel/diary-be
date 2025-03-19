create table daangn.channel_messages
(
  id         varchar(13)  not null comment '아이디'
        primary key,
  channel_id varchar(13)  not null comment '채널 아이디',
  writer_id  varchar(13)  not null comment '메세지 작성 회원 아이디',
  type       varchar(20)  not null comment '메세지 유형',
  content    varchar(255) not null comment '메세지 내용',
  deleted    bit          not null comment '삭제 여부',
  created_at datetime     not null comment '생성 일자',
  updated_at datetime     not null comment '수정 일자'
)
  comment '채널 메세지 테이블';

create index channel_messages_channel_id_index
  on daangn.channel_messages (channel_id);

create table daangn.channel_messages_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  channel_id    varchar(13)  not null,
  writer_id     varchar(13)  not null,
  type          varchar(20)  not null,
  content       varchar(255) not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
)
  comment '채널 메세지 이력 테이블';

