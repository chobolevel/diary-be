create table diary.user_images
(
  id         varchar(13)  not null comment '아이디'
        primary key,
  user_id    varchar(13)  not null comment '회원 아이디',
  name       varchar(40)  not null comment '파일명',
  width      int          not null comment '파일 너비',
  height     int          not null comment '파일 높이',
  url        varchar(255) not null comment '파일 경로',
  `order`    int          not null comment '정렬 순서',
  deleted    bit          not null comment '삭제 여부',
  created_at datetime     not null comment '생성 일자',
  updated_at datetime     not null comment '수정 일자'
)
  comment '회원 이미지 테이블';

create index user_images_user_id_index
  on diary.user_images (user_id);

create table diary.user_images_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  user_id       varchar(13)  not null,
  name          varchar(40)  not null,
  width         int          not null,
  height        int          not null,
  url           varchar(255) not null,
  `order`       int          not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
)
  comment '회원 이미지 이력 테이블';

