create table scrimmers.teams_images
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
  constraint teams_images_teams_id_fk
    foreign key (team_id) references scrimmers.teams (id)
) comment '팀 이미지 테이블';

create table scrimmers.teams_images_histories
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
  updated_at    datetime null,
  primary key (id, revision_id)
) comment '팀 이미지 내역 테이블';

