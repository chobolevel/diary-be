create table diary.product_options
(
  id         varchar(13) not null comment '아이디'
        primary key,
  product_id varchar(13) not null comment '상품 아이디',
  name       varchar(40) not null comment '상품 옵션명',
  `order`    int         not null comment '정렬 순서',
  deleted    bit         not null comment '삭제 여부',
  created_at datetime    not null comment '생성 일자',
  updated_at datetime    not null comment '수정 일자'
)
  comment '상품 옵션 테이블';

create index product_options_product_id_index
  on diary.product_options (product_id);

create table diary.product_options_histories
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  product_id    varchar(13) not null,
  name          varchar(40) not null,
  `order`       int         not null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
)
  comment '상품 옵션 이력 테이블';

