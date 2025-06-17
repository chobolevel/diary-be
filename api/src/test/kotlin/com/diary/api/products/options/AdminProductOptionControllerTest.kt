package com.diary.api.products.options

import com.diary.api.controller.v1.admin.products.AdminProductOptionController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.api.dto.products.options.UpdateProductOptionRequestDto
import com.diary.api.products.DummyProduct
import com.diary.api.service.products.ProductOptionService
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.options.ProductOption
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
@DisplayName("관리자 상품 옵션 컨트롤러 단위 테스트")
class AdminProductOptionControllerTest {

    private val dummyProduct: Product = DummyProduct.toEntity()

    private val dummyProductOption: ProductOption = DummyProductOption.toEntity()

    @Mock
    private lateinit var service: ProductOptionService

    @InjectMocks
    private lateinit var controller: AdminProductOptionController

    @Test
    fun `상품 옵션 등록`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val dummyProductOptionId: ID = dummyProductOption.id
        val request: CreateProductOptionRequestDto = DummyProductOption.toCreateRequestDto()
        `when`(
            service.create(
                productId = dummyProductId,
                request = request
            )
        ).thenReturn(dummyProductOptionId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            productId = dummyProductId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyProductOptionId)
    }

    @Test
    fun `상품 옵션 정보 수정`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val dummyProductOptionId: ID = dummyProductOption.id
        val request: UpdateProductOptionRequestDto = DummyProductOption.toUpdateRequestDto()
        `when`(
            service.update(
                productId = dummyProductId,
                productOptionId = dummyProductOptionId,
                request = request
            )
        ).thenReturn(dummyProductOptionId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            productId = dummyProductId,
            productOptionId = dummyProductOptionId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyProductOptionId)
    }

    @Test
    fun `상품 옵션 삭제`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val dummyProductOptionId: ID = dummyProductOption.id
        `when`(
            service.delete(
                productId = dummyProductId,
                productOptionId = dummyProductOptionId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(
            productId = dummyProductId,
            productOptionId = dummyProductOptionId,
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
