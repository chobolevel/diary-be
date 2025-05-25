package com.diary.api.diaries.likes

import com.diary.api.diaries.DummyDiary
import com.diary.api.users.DummyUser
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.likes.DiaryLike
import com.diary.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("일기 좋아요 엔티티 단위 테스트")
class DiaryLikeEntityTest {

    private val dummyDiaryLike: DiaryLike = DummyDiaryLike.toEntity()

    @Test
    fun `일기 좋아요 엔티티 일기 엔티티 매핑`() {
        // given
        val dummyDiary: Diary = DummyDiary.toEntity()

        // when
        dummyDiaryLike.set(diary = dummyDiary)

        // then
        assertThat(dummyDiaryLike.diary).isEqualTo(dummyDiary)
    }

    @Test
    fun `일기 좋아요 엔티티 회원 엔티티 매핑`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyDiaryLike.set(user = dummyUser)

        // then
        assertThat(dummyDiaryLike.user).isEqualTo(dummyUser)
    }

    @Test
    fun `일기 좋아요 엔티티 삭제`() {
        // given

        // when
        dummyDiaryLike.delete()

        // then
        assertThat(dummyDiaryLike.deleted).isTrue()
    }
}
