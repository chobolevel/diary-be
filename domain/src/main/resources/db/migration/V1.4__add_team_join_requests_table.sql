create table scrimmers.team_join_requests
(
  id            varchar(13)  not null
    primary key,
  team_id       varchar(13)  not null,
  user_id       varchar(13)  not null,
  status        varchar(10)  not null,
  comment       varchar(255) not null,
  reject_reason varchar(255) null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null
) comment '팀 합류 요청 테이블';

create index team_join_requests_team_id_index
  on scrimmers.team_join_requests (team_id);

create index team_join_requests_user_id_index
  on scrimmers.team_join_requests (user_id);

create table scrimmers.team_join_requests_histories
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  team_id       varchar(13) not null,
  user_id       varchar(13) not null,
  status        varchar(10) not null,
  comment       text        not null,
  reject_reason varchar(255) null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
) comment '팀 합류 요청 내역 테이블';

