package com.diary.api.diaries.images

import com.diary.api.diaries.DummyDiary
import com.diary.api.dto.diaries.images.CreateDiaryImageRequestDto
import com.diary.api.dto.diaries.images.UpdateDiaryImageRequestDto
import com.diary.api.service.diaries.DiaryImageService
import com.diary.api.service.diaries.converter.DiaryImageConverter
import com.diary.api.service.diaries.updater.DiaryImageUpdater
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryRepositoryWrapper
import com.diary.domain.entity.diaries.images.DiaryImage
import com.diary.domain.entity.diaries.images.DiaryImageRepositoryWrapper
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("일기 이미지 서비스 로직 단위 테스트")
class DiaryImageServiceTest {

    private val dummyDiary: Diary = DummyDiary.toEntity()

    private val dummyDiaryImage: DiaryImage = DummyDiaryImage.toEntity()

    @Mock
    private lateinit var repositoryWrapper: DiaryImageRepositoryWrapper

    @Mock
    private lateinit var diaryRepositoryWrapper: DiaryRepositoryWrapper

    @Mock
    private lateinit var converter: DiaryImageConverter

    @Mock
    private lateinit var updater: DiaryImageUpdater

    @InjectMocks
    private lateinit var service: DiaryImageService

    @Test
    fun `일기 이미지 등록`() {
        // given
        val dummyDiaryId: ID = dummyDiary.id
        val request: CreateDiaryImageRequestDto = DummyDiaryImage.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyDiaryImage)
        `when`(diaryRepositoryWrapper.findById(id = dummyDiaryId)).thenReturn(dummyDiary)
        `when`(repositoryWrapper.save(diaryImage = dummyDiaryImage)).thenReturn(dummyDiaryImage)

        // when
        val result: ID = service.create(
            diaryId = dummyDiaryId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyDiaryImage.id)
    }

    @Test
    fun `일기 이미지 수정`() {
        // given
        val dummyDiaryId: ID = dummyDiary.id
        val dummyDiaryImageId: ID = dummyDiaryImage.id
        val request: UpdateDiaryImageRequestDto = DummyDiaryImage.toUpdateRequestDto()
        `when`(
            repositoryWrapper.findByIdAndDiaryId(
                id = dummyDiaryImageId,
                diaryId = dummyDiaryId
            )
        ).thenReturn(dummyDiaryImage)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyDiaryImage
            )
        ).thenReturn(dummyDiaryImage)

        // when
        val result: ID = service.update(
            diaryId = dummyDiaryId,
            diaryImageId = dummyDiaryImageId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyDiaryImageId)
    }

    @Test
    fun `일기 이미지 삭제`() {
        // given
        val dummyDiaryId: ID = dummyDiary.id
        val dummyDiaryImageId: ID = dummyDiaryImage.id
        `when`(
            repositoryWrapper.findByIdAndDiaryId(
                id = dummyDiaryImageId,
                diaryId = dummyDiaryId
            )
        ).thenReturn(dummyDiaryImage)

        // when
        val result: Boolean = service.delete(
            diaryId = dummyDiaryId,
            diaryImageId = dummyDiaryImageId
        )

        // then
        assertThat(result).isTrue()
    }
}
