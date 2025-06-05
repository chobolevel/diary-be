create table diary.user_points
(
  id         varchar(13)  not null comment '아이디'
        primary key,
  user_id    varchar(13)  not null comment '회원 아이디',
  amount     int          not null comment '포인트',
  reason     varchar(255) not null comment '포인트 지급/회수 사유',
  deleted    bit          not null comment '삭제 여부',
  created_at datetime     not null comment '생성 일자',
  updated_at datetime     not null comment '수정 일자'
)
  comment '회원 포인트 테이블';

create index user_points_user_id_index
  on diary.user_points (user_id);

