package com.diary.api.diaries.images

import com.diary.api.controller.v1.common.diaries.DiaryImageController
import com.diary.api.diaries.DummyDiary
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.diaries.images.CreateDiaryImageRequestDto
import com.diary.api.dto.diaries.images.UpdateDiaryImageRequestDto
import com.diary.api.service.diaries.DiaryImageService
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.images.DiaryImage
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
@DisplayName("일기 이미지 컨트롤러 단위 테스트")
class DiaryImageControllerTest {

    private val dummyDiary: Diary = DummyDiary.toEntity()

    private val dummyDiaryImage: DiaryImage = DummyDiaryImage.toEntity()

    @Mock
    private lateinit var service: DiaryImageService

    @InjectMocks
    private lateinit var controller: DiaryImageController

    @Test
    fun `일기 이미지 등록`() {
        // given
        val dummyDiaryId: ID = dummyDiary.id
        val dummyDiaryImageId: ID = dummyDiaryImage.id
        val request: CreateDiaryImageRequestDto = DummyDiaryImage.toCreateRequestDto()
        `when`(
            service.create(
                diaryId = dummyDiaryId,
                request = request
            )
        ).thenReturn(dummyDiaryImageId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            diaryId = dummyDiaryId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyDiaryImageId)
    }

    @Test
    fun `일기 이미지 수정`() {
        // given
        val dummyDiaryId: ID = dummyDiary.id
        val dummyDiaryImageId: ID = dummyDiaryImage.id
        val request: UpdateDiaryImageRequestDto = DummyDiaryImage.toUpdateRequestDto()
        `when`(
            service.update(
                diaryId = dummyDiaryId,
                diaryImageId = dummyDiaryImageId,
                request = request
            )
        ).thenReturn(dummyDiaryImageId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            diaryId = dummyDiaryId,
            diaryImageId = dummyDiaryImageId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyDiaryImageId)
    }

    @Test
    fun `일기 이미지 삭제`() {
        // given
        val dummyDiaryId: ID = dummyDiary.id
        val dummyDiaryImageId: ID = dummyDiaryImage.id
        `when`(
            service.delete(
                diaryId = dummyDiaryId,
                diaryImageId = dummyDiaryImageId,
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(
            diaryId = dummyDiaryId,
            diaryImageId = dummyDiaryImageId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
