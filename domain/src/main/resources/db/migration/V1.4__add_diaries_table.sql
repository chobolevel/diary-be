create table diary.diaries
(
  id         varchar(13) not null comment '아이디'
        primary key,
  writer_id  varchar(13) not null comment '작성자(회원) 아이디',
  weather_id varchar(13) not null comment '날씨 아이디',
  emotion_id varchar(13) not null comment '감정 아이디',
  title      varchar(80) not null comment '일기 제목',
  content    text        not null comment '일기 내용',
  is_sercet  bit         not null comment '비밀글 여부',
  deleted    bit         not null comment '삭제 여부',
  created_at datetime    not null comment '생성 일자',
  updated_at datetime    not null comment '수정 일자'
)
  comment '일기 테이블';

create index diaries_emotion_id_index
  on diary.diaries (emotion_id);

create index diaries_weather_id_index
  on diary.diaries (weather_id);

create index diaries_writer_id_index
  on diary.diaries (writer_id);

create table diary.diaries_hisotires
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  writer_id     varchar(13) not null,
  weather_id    varchar(13) not null,
  emotion_id    varchar(13) not null,
  title         varchar(80) not null,
  content       text        not null,
  is_secret     bit         not null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
)
  comment '일기 이력 테이블';

