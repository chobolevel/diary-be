create table scrimmers.board_categories
(
  id         varchar(13)  not null
    primary key,
  code       varchar(100) not null,
  name       varchar(100) not null,
  `order`    int          not null,
  deleted    bit          not null,
  created_at datetime     not null,
  updated_at datetime     not null
) comment '게시판 카테고리 테이블';

create index board_categories_code_index
  on scrimmers.board_categories (code);

create index board_categories_name_index
  on scrimmers.board_categories (name);

create table scrimmers.board_categories_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  code          varchar(100) not null,
  name          varchar(100) not null,
  `order`       int          not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
) comment '게시판 카테고리 이력 테이블';

