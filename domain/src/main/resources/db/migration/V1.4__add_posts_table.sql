create table daangn.posts
(
  id          varchar(13)  not null
    primary key,
  writer_id   varchar(13)  not null comment '작성자 아이디',
  category_id varchar(13)  not null comment '카테고리 아이디',
  status      varchar(20)  not null comment '판매 상태',
  title       varchar(100) not null comment '게시글 제목',
  content     text         not null comment '게시글 내용',
  sale_price  int          null comment '판매 가격',
  free_shared bit          not null comment '무료 나눔 여부',
  deleted     bit          not null comment '삭제 여부',
  created_at  datetime     not null comment '생성 일자',
  updated_at  datetime     not null comment '수정 일자'
)
  comment '게시글 테이블';

create index posts_category_id_index
  on daangn.posts (category_id);

create index posts_status_index
  on daangn.posts (status);

create index posts_title_index
  on daangn.posts (title);

create index posts_writer_id_index
  on daangn.posts (writer_id);

create table daangn.posts_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  writer_id     varchar(13)  not null,
  category_id   varchar(13)  not null,
  status        varchar(20)  not null,
  title         varchar(100) not null,
  content       text         not null,
  sale_price    int          not null,
  free_shared   bit          not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
)
  comment '게시글 이력 테이블';

