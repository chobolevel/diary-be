create table diary.emotions
(
  id         varchar(13) not null comment '아이디'
        primary key,
  name       varchar(40) not null comment '감정명',
  icon       varchar(40) not null comment '감정 아이콘',
  `order`    int         not null comment '정렬 순서',
  deleted    bit         not null comment '삭제 여부',
  created_at datetime    not null comment '생성 일자',
  updated_at datetime    not null comment '수정 일자'
)
  comment '감정 테이블';

create table diary.emotions_histories
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  name          varchar(40) not null,
  icon          varchar(40) not null,
  `order`       int         not null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
)
  comment '감정 이력 테이블';

