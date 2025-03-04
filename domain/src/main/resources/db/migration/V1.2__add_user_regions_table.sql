create table daangn.user_regions
(
  id                 varchar(13)  not null comment '아이디'
        primary key,
  user_id            varchar(13)  not null comment '회원 아이디',
  latitude           double       not null comment '위도',
  longitude          double       not null comment '경도',
  region_1depth_name varchar(100) not null comment '시/도',
  region_2depth_name varchar(100) not null comment '구',
  region_3depth_name varchar(100) not null comment '읍/면/동',
  deleted            bit          not null comment '삭제 여부',
  created_at         datetime     not null comment '생성 일자',
  updated_at         datetime     not null comment '수정 일자'
)
  comment '회원 지역 테이블';

create index users_regions_user_id_index
  on daangn.user_regions (user_id);

create table daangn.user_regions_histories
(
  id                 varchar(13)  not null,
  revision_id        bigint       not null,
  revision_type      tinyint      not null,
  user_id            varchar(13)  not null,
  latitude           double       not null,
  longitude          double       not null,
  region_1depth_name varchar(100) not null,
  region_2depth_name varchar(100) not null,
  region_3depth_name varchar(100) not null,
  deleted            bit          not null,
  created_at         datetime     not null,
  updated_at         datetime     not null,
  primary key (id, revision_id)
)
  comment '회원 지역 이력 테이블';

