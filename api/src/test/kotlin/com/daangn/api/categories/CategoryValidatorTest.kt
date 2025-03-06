package com.daangn.api.categories

import com.daangn.api.dto.categories.CreateCategoryRequestDto
import com.daangn.api.dto.categories.UpdateCategoryRequestDto
import com.daangn.api.service.categories.validator.CategoryValidator
import com.daangn.domain.entity.categories.CategoryUpdateMask
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
@DisplayName("카테고리 검증기 단위 테스트")
class CategoryValidatorTest {

    private lateinit var validator: CategoryValidator

    @BeforeEach
    fun setup() {
        validator = CategoryValidator()
    }

    @Test
    fun `카테고리 등록 시 카테고리명 올바르지 않은 케이스`() {
        // given
        val request = CreateCategoryRequestDto(
            iconUrl = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2024/08/30/93c5e6d9-a261-4179-967c-c032e859194e.jpg",
            name = "디지털기기!",
            order = 1
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[name]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `카테고리 등록 시 정렬 순서 올바르지 않은 케이스`() {
        // given
        val request = CreateCategoryRequestDto(
            iconUrl = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2024/08/30/93c5e6d9-a261-4179-967c-c032e859194e.jpg",
            name = "디지털기기",
            order = 0
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 반드시 0보다 커야 합니다.")
    }

    @Test
    fun `카테고리 아이콘 경로 수정 시 NULL 케이스`() {
        // given
        val request = UpdateCategoryRequestDto(
            iconUrl = null,
            name = null,
            order = null,
            updateMask = listOf(
                CategoryUpdateMask.ICON_URL
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[icon_url]은(는) 필수 값입니다.")
    }

    @Test
    fun `카테고리명 수정 시 NULL 케이스`() {
        // given
        val request = UpdateCategoryRequestDto(
            iconUrl = null,
            name = null,
            order = null,
            updateMask = listOf(
                CategoryUpdateMask.NAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[name]은(는) 필수 값입니다.")
    }

    @Test
    fun `카테고리 수정 시 올바르지 않은 케이스`() {
        // given
        val request = UpdateCategoryRequestDto(
            iconUrl = null,
            name = "디지털기기!",
            order = null,
            updateMask = listOf(
                CategoryUpdateMask.NAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[name]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `카테고리 수정 시 정렬 순서 NULL 케이스`() {
        // given
        val request = UpdateCategoryRequestDto(
            iconUrl = null,
            name = null,
            order = null,
            updateMask = listOf(
                CategoryUpdateMask.ORDER
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 필수 값입니다.")
    }

    @Test
    fun `카테고리 수정 시 정렬 순서 올바르지 않은 케이스`() {
        // given
        val request = UpdateCategoryRequestDto(
            iconUrl = null,
            name = null,
            order = 0,
            updateMask = listOf(
                CategoryUpdateMask.ORDER
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 반드시 0보다 커야 합니다.")
    }
}
