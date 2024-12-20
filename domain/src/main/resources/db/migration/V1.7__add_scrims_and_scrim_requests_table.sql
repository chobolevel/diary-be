create table scrimmers.scrim_requests
(
  id            varchar(13)  not null
    primary key,
  from_team_id  varchar(13)  not null,
  to_team_id    varchar(13)  not null,
  status        varchar(10)  not null,
  comment       varchar(255) not null,
  reject_reason varchar(255) null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  constraint scrim_requests_teams_id_fk
    foreign key (from_team_id) references scrimmers.teams (id),
  constraint scrim_requests_teams_id_fk_2
    foreign key (to_team_id) references scrimmers.teams (id)
) comment '스크림 요청 테이블';

create table scrimmers.scrim_requests_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  from_team_id  varchar(13)  not null,
  to_team_id    varchar(13)  not null,
  status        varchar(10)  not null,
  comment       varchar(255) not null,
  reject_reason varchar(255) null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
) comment '스크림 요청 내역 테이블';

create table scrimmers.scrims
(
  id               varchar(13)  not null
    primary key,
  scrim_request_id varchar(13)  not null,
  home_team_id     varchar(13)  not null,
  away_team_id     varchar(13)  not null,
  type             varchar(10)  not null,
  name             varchar(255) not null,
  started_at       datetime     not null,
  deleted          bit          not null,
  created_at       datetime     not null,
  updated_at       datetime     not null,
  constraint scrims_scrim_requests_id_fk
    foreign key (scrim_request_id) references scrimmers.scrim_requests (id),
  constraint scrims_teams_id_fk
    foreign key (home_team_id) references scrimmers.teams (id),
  constraint scrims_teams_id_fk_2
    foreign key (away_team_id) references scrimmers.teams (id)
) comment '스크림 테이블';

create table scrimmers.scrims_histories
(
  id               varchar(13)  not null,
  revision_id      bigint       not null,
  revision_type    tinyint      not null,
  scrim_request_id varchar(13)  not null,
  home_team_id     varchar(13)  not null,
  away_team_id     varchar(13)  not null,
  type             varchar(10)  not null,
  name             varchar(255) not null,
  started_at       datetime     not null,
  deleted          bit          not null,
  created_at       datetime     not null,
  updated_at       datetime     not null,
  primary key (id, revision_id)
) comment '스크림 내역 테이블';

