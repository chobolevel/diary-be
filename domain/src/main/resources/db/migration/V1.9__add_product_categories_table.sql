create table diary.product_categories
(
  id         varchar(13)  not null comment '아이디'
        primary key,
  parent_id  varchar(13)  null comment '부모 카테고리 아이디',
  name       varchar(40)  not null comment '카테고리명',
  image_url  varchar(255) null comment '카테고리 이미지 경로',
  `order`    int          not null comment '정렬 순서',
  deleted    bit          not null comment '삭제 여부',
  created_at datetime     not null comment '생성일자',
  updated_at datetime     not null comment '수정일자'
)
  comment '상품 카테고리 테이블';

create table diary.product_categories_histories
(
  id            varchar(13)  not null,
  revision_id   bigint       not null,
  revision_type tinyint      not null,
  parent_id     varchar(13)  null,
  name          varchar(40)  not null,
  image_url     varchar(255) null,
  `order`       int          not null,
  deleted       bit          not null,
  created_at    datetime     not null,
  updated_at    datetime     not null,
  primary key (id, revision_id)
)
  comment '상품 카테고리 이력 테이블';

