create table scrimmers.teams
(
  id          varchar(13)  not null
    primary key,
  owner_id    varchar(13)  not null,
  name        varchar(255) not null,
  description text         not null,
  deleted     bit          not null,
  created_at  datetime     not null,
  updated_at  datetime     not null,
  constraint teams_users_id_fk
    foreign key (owner_id) references scrimmers.users (id)
) comment '회원 소속팀 테이블';

create table scrimmers.teams_histories
(
  id            varchar(100) not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  owner_id      varchar(13)  not null,
  name          varchar(255) not null,
  description   text         not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
) comment '회원 소속팀 내역 테이블';

create table scrimmers.team_images
(
  id         varchar(13)  not null
    primary key,
  team_id    varchar(13)  not null,
  type       varchar(100) not null,
  name       varchar(255) not null,
  url        varchar(255) not null,
  deleted    bit          not null,
  created_at datetime     not null,
  updated_at datetime null,
  constraint team_images_teams_id_fk
    foreign key (team_id) references scrimmers.teams (id)
) comment '팀 이미지 테이블';

create table scrimmers.team_images_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  team_id       varchar(13)  not null,
  type          varchar(100) not null,
  name          varchar(255) not null,
  url           varchar(255) not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at datetime null,
  primary key (id, revision_id)
) comment '팀 이미지 내역 테이블';

