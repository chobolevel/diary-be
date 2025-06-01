create table diary.diary_images
(
  id         varchar(13)  not null comment '아이디'
        primary key,
  diary_id   varchar(13)  not null comment '일기 아이디',
  name       varchar(40)  not null comment '이미지명',
  width      int          not null comment '이미지 너비',
  height     int          not null comment '이미지 높이',
  url        varchar(255) not null comment '이미지 경로',
  `order`    int          not null comment '정렬 순서',
  deleted    bit          not null comment '삭제 여부',
  created_at datetime     not null comment '생성 일자',
  updated_at datetime     not null comment '수정 일자'
)
  comment '일기 이미지 테이블';

create index diary_images_diary_id_index
  on diary.diary_images (diary_id);

create table diary.diary_images_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  diary_id      varchar(13)  not null,
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
  comment '일기 이미지 이력 테이블';

