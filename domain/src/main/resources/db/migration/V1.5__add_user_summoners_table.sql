create table scrimmers.user_summoners
(
  id                 varchar(13)  not null
    primary key,
  user_id            varchar(13)  not null,
  summoner_id        varchar(100) not null,
  summoner_name      varchar(255) not null,
  summoner_tag       varchar(3)   not null,
  summoner_level     int          not null,
  summoner_icon_id int not null,
  summoner_solo_rank varchar(100) not null,
  summoner_flex_rank varchar(100) not null,
  deleted            bit          not null,
  created_at         datetime     not null,
  updated_at         datetime     not null
);

create index user_summoners_user_id_index
  on scrimmers.user_summoners (user_id);

create table scrimmers.user_summoners_histories
(
  id                 varchar(13)  not null,
  revision_id        bigint       not null,
  revision_type      tinyint      not null,
  user_id            varchar(13)  not null,
  summoner_id        varchar(255) not null,
  summoner_name      varchar(255) not null,
  summoner_tag       varchar(3)   not null,
  summoner_level     int          not null,
  summoner_icon_id int not null,
  summoner_solo_rank varchar(100) not null,
  summoner_flex_rank varchar(100) not null,
  deleted            bit          not null,
  created_at         datetime     not null,
  updated_at         datetime     not null,
  primary key (id, revision_id)
) comment '회원 소환사 내역 테이블';

