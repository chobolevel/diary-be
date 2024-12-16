create table scrimmers.users
(
  id         varchar(13)  not null
    primary key,
  email      varchar(255) not null,
  password   varchar(255) null,
  social_id  varchar(255) null,
  login_type varchar(100) not null,
  nickname   varchar(100) not null,
  phone      varchar(100) not null,
  role       varchar(100) not null,
  resigned   bit          not null,
  created_at datetime     not null,
  updated_at datetime     not null
) comment '회원 테이블';

create index users_email_login_type_index
  on scrimmers.users (email, login_type);

create index users_social_id_login_type_index
  on scrimmers.users (social_id, login_type);

create table scrimmers.users_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  email         varchar(255) not null,
  password      varchar(255) null,
  social_id     varchar(255) null,
  login_type    varchar(100) not null,
  nickname      varchar(100) not null,
  phone         varchar(100) not null,
  role          varchar(100) not null,
  resigned      bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
) comment '회원 이력 테이블';

