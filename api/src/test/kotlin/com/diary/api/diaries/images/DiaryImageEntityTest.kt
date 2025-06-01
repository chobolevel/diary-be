package com.diary.api.diaries.images

import com.diary.api.diaries.DummyDiary
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.images.DiaryImage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("일기 이미지 엔티티 단위 테스트")
class DiaryImageEntityTest {

    private val dummyDiaryImage: DiaryImage = DummyDiaryImage.toEntity()

    @Test
    fun `일기 이미지 엔티티 일기 엔티티 매핑`() {
        // given
        val dummyDiary: Diary = DummyDiary.toEntity()

        // when
        dummyDiaryImage.set(diary = dummyDiary)

        // then
        assertThat(dummyDiaryImage.diary).isEqualTo(dummyDiary)
    }

    @Test
    fun `일기 이미지 엔티티 삭제`() {
        // given

        // when
        dummyDiaryImage.delete()

        // then
        assertThat(dummyDiaryImage.deleted).isTrue()
    }
}
