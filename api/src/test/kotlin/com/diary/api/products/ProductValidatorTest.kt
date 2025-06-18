package com.diary.api.products

import com.diary.api.dto.products.CreateProductRequestDto
import com.diary.api.dto.products.UpdateProductRequestDto
import com.diary.api.products.options.DummyProductOption
import com.diary.api.service.products.validator.ProductValidator
import com.diary.domain.entity.products.ProductUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("상품 validation 단위 테스트")
class ProductValidatorTest {

    private val validator: ProductValidator = ProductValidator()

    @Test
    fun `상품 등록 시 정렬 순서 1 미만인 케이스`() {
        // given
        val request = CreateProductRequestDto(
            productCategoryId = "0KH4WDSJA2CHB",
            name = "2025 T1 Uniform Jersey",
            order = 0,
            options = listOf(DummyProductOption.toCreateRequestDto())
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 반드시 1 이상이어야 합니다.")
    }

    @Test
    fun `상품 카테고리 아이디 수정 시 NULL 케이스`() {
        // given
        val request = UpdateProductRequestDto(
            productCategoryId = null,
            name = null,
            status = null,
            order = null,
            updateMask = listOf(ProductUpdateMask.CATEGORY)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[product_category_id]은(는) 필수 값입니다.")
    }

    @Test
    fun `상품명 수정 시 NULL 케이스`() {
        // given
        val request = UpdateProductRequestDto(
            productCategoryId = null,
            name = null,
            status = null,
            order = null,
            updateMask = listOf(ProductUpdateMask.NAME)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[name]은(는) 필수 값입니다.")
    }

    @Test
    fun `상품 상태 수정 시 NULL 케이스`() {
        // given
        val request = UpdateProductRequestDto(
            productCategoryId = null,
            name = null,
            status = null,
            order = null,
            updateMask = listOf(ProductUpdateMask.STATUS)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[status]은(는) 필수 값입니다.")
    }

    @Test
    fun `상품 정렬 순서 수정 시 NULL 케이스`() {
        // given
        val request = UpdateProductRequestDto(
            productCategoryId = null,
            name = null,
            status = null,
            order = null,
            updateMask = listOf(ProductUpdateMask.ORDER)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 필수 값입니다.")
    }

    @Test
    fun `상품 정렬 순서 수정 시 1 미만인 케이스`() {
        // given
        val request = UpdateProductRequestDto(
            productCategoryId = null,
            name = null,
            status = null,
            order = 0,
            updateMask = listOf(ProductUpdateMask.ORDER)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 반드시 1 이상이어야 합니다.")
    }
}
