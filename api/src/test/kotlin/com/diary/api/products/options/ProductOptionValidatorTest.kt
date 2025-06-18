package com.diary.api.products.options

import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.api.dto.products.options.UpdateProductOptionRequestDto
import com.diary.api.products.options.values.DummyProductOptionValue
import com.diary.api.service.products.validator.ProductOptionValidator
import com.diary.domain.entity.products.options.ProductOptionUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("상품 옵션 validation 단위 테스트")
class ProductOptionValidatorTest {

    private val validator: ProductOptionValidator = ProductOptionValidator()

    @Test
    fun `상품 옵션 등록 시 정렬 순서 1 미만인 케이스`() {
        // given
        val request = CreateProductOptionRequestDto(
            name = "사이즈",
            order = 0,
            values = listOf(DummyProductOptionValue.toCreateRequestDto())
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
    fun `상품 옵션명 수정 시 NULL 케이스`() {
        // given
        val request = UpdateProductOptionRequestDto(
            name = null,
            order = null,
            updateMask = listOf(ProductOptionUpdateMask.NAME)
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
    fun `상품 옵션 정렬 순서 수정 시 NULL 케이스`() {
        // given
        val request = UpdateProductOptionRequestDto(
            name = null,
            order = null,
            updateMask = listOf(ProductOptionUpdateMask.ORDER)
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
    fun `상품 옵션 정렬 순서 수정 시 1 미만인 케이스`() {
        // given
        val request = UpdateProductOptionRequestDto(
            name = null,
            order = 0,
            updateMask = listOf(ProductOptionUpdateMask.ORDER)
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
