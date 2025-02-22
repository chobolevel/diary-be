create table daangn.users
(
  id           varchar(13)  not null comment '아이디'
        primary key,
  email        varchar(40)  not null comment '이메일(아이디)',
  password     varchar(100) null comment '비밀번호',
  social_id    varchar(100) null comment '소셜 아이디',
  sign_up_type varchar(10)  not null comment '회원가입 유형',
  nickname     varchar(20)  not null comment '닉네임',
  role         varchar(10)  not null comment '권한',
  resigned     bit          not null comment '탈퇴 여부',
  created_at   datetime     not null comment '생성 일자',
  updated_at   datetime     not null comment '수정 일자'
)
  comment '회원 테이블';

create index users_email_index
  on daangn.users (email);

create table daangn.users_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  email         varchar(40)  not null,
  password      varchar(100) null,
  social_id     varchar(100) null,
  sign_up_type  varchar(10)  not null,
  nickname      varchar(20)  not null,
  role          varchar(10)  not null,
  resigned      bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null
)
  comment '회원 이력 테이블';

