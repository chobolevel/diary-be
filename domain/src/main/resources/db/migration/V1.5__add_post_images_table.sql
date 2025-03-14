create table daangn.post_images
(
  id         varchar(13)  not null comment '아이디'
        primary key,
  post_id    varchar(13)  not null comment '게시글 아이디',
  type       varchar(20)  not null comment '게시글 이미지 유형',
  url        varchar(255) not null comment '게시글 이미지 경로',
  `order`    int          not null comment '정렬 순서',
  deleted    bit          not null comment '삭제 여부',
  created_at datetime     not null comment '생성 일자',
  updated_at datetime     not null comment '수정 일자'
)
  comment '게시글 이미지 테이블';

create index post_images_post_id_index
  on daangn.post_images (post_id);

create table daangn.post_images_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  post_id       varchar(13)  not null,
  type          varchar(20)  not null,
  url           varchar(255) not null,
  `order`       int          not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null
)
  comment '게시글 이미지 이력 테이블';

