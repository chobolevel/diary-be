create table daangn.post_likes
(
  id         varchar(13) not null comment '아이디'
        primary key,
  post_id    varchar(13) not null comment '게시글 아이디',
  user_id    varchar(13) not null comment '회원 아이디',
  deleted    bit         not null comment '삭제 여부',
  created_at datetime    not null comment '생성 일자',
  updated_at datetime    not null comment '수정 일자'
)
  comment '게시글 좋아요 테이블';

create index post_likes_post_id_index
  on daangn.post_likes (post_id);

create index post_likes_user_id_index
  on daangn.post_likes (user_id);

create table daangn.post_likes_histories
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  post_id       varchar(13) not null,
  user_id       varchar(13) not null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
)
  comment '게시글 좋아요 이력 테이블';

