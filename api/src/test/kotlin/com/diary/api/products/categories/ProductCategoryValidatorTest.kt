package com.diary.api.products.categories

import com.diary.api.dto.products.categories.CreateProductCategoryRequestDto
import com.diary.api.dto.products.categories.UpdateProductCategoryRequestDto
import com.diary.api.service.products.validator.ProductCategoryValidator
import com.diary.domain.entity.products.categories.ProductCategoryUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("상품 카테고리 validation 단위 테스트")
class ProductCategoryValidatorTest {

    private val validator: ProductCategoryValidator = ProductCategoryValidator()

    @Test
    fun `상품 카테고리 등록 시 정렬 순서 1 미만인 케이스`() {
        // given
        val request = CreateProductCategoryRequestDto(
            parentId = null,
            name = "조명",
            imageUrl = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2025/05/26/9604d334-4fa3-4b17-8dd0-205ca309ed18.jpeg",
            order = 0
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
    fun `상품 카테고리명 수정 시 NULL 케이스`() {
        // given
        val request = UpdateProductCategoryRequestDto(
            parentId = null,
            name = null,
            imageUrl = null,
            order = null,
            updateMask = listOf(ProductCategoryUpdateMask.NAME)
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
    fun `상품 카테고리 정렬 순서 수정 시 NULL 케이스`() {
        // given
        val request = UpdateProductCategoryRequestDto(
            parentId = null,
            name = null,
            imageUrl = null,
            order = null,
            updateMask = listOf(ProductCategoryUpdateMask.ORDER)
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
    fun `상품 카테고리 정렬 순서 수정 시 1 미만인 케이스`() {
        // given
        val request = UpdateProductCategoryRequestDto(
            parentId = null,
            name = null,
            imageUrl = null,
            order = 0,
            updateMask = listOf(ProductCategoryUpdateMask.ORDER)
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
