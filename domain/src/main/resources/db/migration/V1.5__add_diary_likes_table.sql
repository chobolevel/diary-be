create table diary.diary_likes
(
  id         varchar(13) not null comment '아이디'
        primary key,
  diary_id   varchar(13) not null comment '일기 아이디',
  user_id    varchar(13) not null comment '회원 아이디',
  deleted    bit         not null comment '삭제 여부',
  created_at datetime    not null comment '생성 일자',
  updated_at datetime    not null comment '수정 일자'
)
  comment '일기 좋아요 테이블';

create index diary_likes_diary_id_index
  on diary.diary_likes (diary_id);

create index diary_likes_user_id_index
  on diary.diary_likes (user_id);

create table diary.diary_likes_histories
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  diary_id      varchar(13) not null,
  user_id       varchar(13) not null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
)
  comment '일기 좋아요 이력 테이블';

