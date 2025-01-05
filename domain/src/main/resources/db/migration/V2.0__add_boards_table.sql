create table scrimmers.boards
(
  id                varchar(13)  not null
    primary key,
  writer_id         varchar(13) null,
  board_category_id varchar(13)  not null,
  title             varchar(100) not null,
  content           text         not null,
  deleted           bit          not null,
  created_at        datetime     not null,
  updated_at        datetime     not null,
  constraint boards_board_categories_id_fk
    foreign key (board_category_id) references scrimmers.board_categories (id),
  constraint boards_users_id_fk
    foreign key (writer_id) references scrimmers.users (id)
) comment '게시판 테이블';

create index boards_title_index
  on scrimmers.boards (title);

CREATE TABLE `boards_histories`
(
  `id`                varchar(13)  NOT NULL,
  `revision_id`       bigint       NOT NULL,
  `revision_type`     tinyint      NOT NULL,
  `writer_id`         varchar(13)  NOT NULL,
  `board_category_id` varchar(13)  NOT NULL,
  `title`             varchar(100) NOT NULL,
  `content`           text         NOT NULL,
  `deleted`           bit(1)       NOT NULL,
  `created_at`        datetime     NOT NULL,
  `updated_at`        datetime     NOT NULL,
  PRIMARY KEY (`id`, `revision_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='게시판 이력 테이블'

