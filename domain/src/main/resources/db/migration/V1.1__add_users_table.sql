create table diary.users
(
  id           varchar(13)  not null comment '아이디'
        primary key,
  username     varchar(30)  not null comment '아이디(로그인)',
  password     varchar(255) not null comment '비밀번호',
  sign_up_type varchar(20)  not null comment '회원 가입 유형',
  nickname     varchar(20)  not null comment '닉네임',
  scope        varchar(20)  not null comment '공개 범위(전체 공개, 친구 공개, 비공개)',
  resigned     bit          not null comment '탈퇴 여부',
  created_at   datetime     not null comment '생성 일자',
  updated_at   datetime     not null comment '수정 일자'
)
  comment '회원 테이블';

create index users_username_index
  on diary.users (username);

create table diary.users_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  username      varchar(30)  not null,
  password      varchar(255) not null,
  sign_up_type  varchar(20)  not null,
  nickname      varchar(20)  not null,
  scope         varchar(20)  not null,
  resigned      bit          not null,
  created_at    datetime     not null comment '생성 일자',
  updated_at    datetime     not null,
  primary key (id, revision_id)
)
  comment '회원 이력 테이블';

