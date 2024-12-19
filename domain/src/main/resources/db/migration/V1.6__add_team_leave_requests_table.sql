create table scrimmers.team_leave_requests
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
  updated_at    datetime null,
  constraint team_leave_requests_teams_id_fk
    foreign key (team_id) references scrimmers.teams (id),
  constraint team_leave_requests_users_id_fk
    foreign key (user_id) references scrimmers.users (id)
) comment '팀 탈퇴 요청 테이블';

create table scrimmers.team_leave_requests_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  team_id       varchar(13)  not null,
  user_id       varchar(13)  not null,
  status        varchar(10)  not null,
  comment       varchar(255) not null,
  reject_reason varchar(255) null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
) comment '팀 탈퇴 요청 내역 테이블';

