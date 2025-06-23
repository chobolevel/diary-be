create table diary.product_variants
(
  id         varchar(13) not null comment '아이디'
        primary key,
  product_id varchar(13) not null comment '상품 아이디',
  stock      int         not null comment '재고 수량',
  deleted    bit         not null comment '삭제 여부',
  created_at datetime    not null comment '생성 일자',
  updated_at datetime    not null comment '수정 일자'
)
  comment '상품 변형 테이블';

create index product_variants_product_id_index
  on diary.product_variants (product_id);

create table diary.product_variants_histories
(
  id            varchar(13) not null,
  revision_id   bigint      not null,
  revision_type tinyint     not null,
  product_id    varchar(13) not null,
  stock         int         not null,
  deleted       bit         not null,
  created_at    datetime    not null,
  updated_at    datetime    not null,
  primary key (id, revision_id)
)
  comment '상품 변형 이력 테이블';

