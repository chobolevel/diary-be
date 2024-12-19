create table scrimmers.user_images
(
  id         varchar(13)  not null
    primary key,
  user_id    varchar(13)  not null,
  type       varchar(100) not null,
  name       varchar(255) not null,
  url        varchar(255) not null,
  deleted    bit          not null,
  created_at datetime     not null,
  updated_at datetime     not null,
  constraint user_images_users_id_fk
    foreign key (user_id) references scrimmers.users (id)
) comment '회원 이미지 테이블';

create table scrimmers.user_images_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  user_id       varchar(13)  not null,
  type          varchar(100) not null,
  name          varchar(255) not null,
  url           varchar(255) not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
) comment '회원 이미지 내역 테이블';

