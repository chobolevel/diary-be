package com.daangn.api.posts

import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.api.service.posts.validator.PostValidator
import com.daangn.domain.entity.posts.PostUpdateMask
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("게시글 검증기 단위 테스트")
class PostValidatorTest {

    private lateinit var validator: PostValidator

    @BeforeEach
    fun setup() {
        validator = PostValidator()
    }

    @Test
    fun `게시글 등록 시 제목 올바르지 않은 케이스`() {
        // given
        val request = CreatePostRequestDto(
            categoryId = "0K0ZMTNDZX7Q4",
            title = "제목@",
            content = "내용을 입력하고 있습니다.",
            salePrice = 10_000,
            freeShared = false,
            mainImages = emptyList()
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[title]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `게시글 등록 시 내용 10자 넘지 않는 케이스`() {
        // given
        val request = CreatePostRequestDto(
            categoryId = "0K0ZMTNDZX7Q4",
            title = "제목",
            content = "내용",
            salePrice = 10_000,
            freeShared = false,
            mainImages = emptyList()
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[content]은(는) 최소 10자 이상이어야 합니다.")
    }

    @Test
    fun `게시글 등록 시 판매 가격 음수 케이스`() {
        // given
        val request = CreatePostRequestDto(
            categoryId = "0K0ZMTNDZX7Q4",
            title = "제목",
            content = "내용을 입력하고 있습니다.",
            salePrice = -10_000,
            freeShared = false,
            mainImages = emptyList()
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[sale_price]은(는) 반드시 0 이상이어야 합니다.")
    }

    @Test
    fun `게시글 카테고리 수정 시 NULL 케이스`() {
        // given
        val request = UpdatePostRequestDto(
            categoryId = null,
            title = null,
            content = null,
            salePrice = null,
            freeShared = null,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.CATEGORY
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[category_id]은(는) 필수 값입니다.")
    }

    @Test
    fun `게시글 제목 수정 시 NULL 케이스`() {
        // given
        val request = UpdatePostRequestDto(
            categoryId = null,
            title = null,
            content = null,
            salePrice = null,
            freeShared = null,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.TITLE
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[title]은(는) 필수 값입니다.")
    }

    @Test
    fun `게시글 제목 수정 시 올바르지 않은 케이스`() {
        // given
        val request = UpdatePostRequestDto(
            categoryId = null,
            title = "제목@",
            content = null,
            salePrice = null,
            freeShared = null,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.TITLE
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[title]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `게시글 내용 수정 시 NULL 케이스`() {
        // given
        val request = UpdatePostRequestDto(
            categoryId = null,
            title = null,
            content = null,
            salePrice = null,
            freeShared = null,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.CONTENT
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[content]은(는) 필수 값입니다.")
    }

    @Test
    fun `게시글 내용 수정 시 내용 10자 넘지 않는 케이스`() {
        // given
        val request = UpdatePostRequestDto(
            categoryId = null,
            title = null,
            content = "내용",
            salePrice = null,
            freeShared = null,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.CONTENT
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[content]은(는) 최소 10자 이상이어야 합니다.")
    }

    @Test
    fun `게시글 판매가 수정 시 NULL 케이스`() {
        // given
        val request = UpdatePostRequestDto(
            categoryId = null,
            title = null,
            content = null,
            salePrice = null,
            freeShared = null,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.SALE_PRICE
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[sale_price]은(는) 필수 값입니다.")
    }

    @Test
    fun `게시글 무료 나눔 여부 수정 시 NULL 케이스`() {
        // given
        val request = UpdatePostRequestDto(
            categoryId = null,
            title = null,
            content = null,
            salePrice = null,
            freeShared = null,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.FREE_SHARED
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[free_shared]은(는) 필수 값입니다.")
    }

    @Test
    fun `게시글 메인 이미지 수정 시 NULL 케이스`() {
        // given
        val request = UpdatePostRequestDto(
            categoryId = null,
            title = null,
            content = null,
            salePrice = null,
            freeShared = null,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.MAIN_IMAGES
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[main_images]은(는) 필수 값입니다.")
    }
}
