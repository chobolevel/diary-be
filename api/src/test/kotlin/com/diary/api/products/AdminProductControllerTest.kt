package com.diary.api.products

import com.diary.api.controller.v1.admin.products.AdminProductController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.CreateProductRequestDto
import com.diary.api.dto.products.ProductResponseDto
import com.diary.api.dto.products.UpdateProductRequestDto
import com.diary.api.service.products.ProductService
import com.diary.domain.entity.products.Product
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
@DisplayName("관리자 상품 컨트롤러 단위 테스트")
class AdminProductControllerTest {

    private val dummyProduct: Product = DummyProduct.toEntity()
    private val dummyProductResponse: ProductResponseDto = DummyProduct.toResponseDto()

    @Mock
    private lateinit var service: ProductService

    @InjectMocks
    private lateinit var controller: AdminProductController

    @Test
    fun `상품 등록`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val request: CreateProductRequestDto = DummyProduct.toCreateRequestDto()
        `when`(service.create(request = request)).thenReturn(dummyProductId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(request = request)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyProductId)
    }

    @Test
    fun `상품 정보 수정`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        val request: UpdateProductRequestDto = DummyProduct.toUpdateRequestDto()
        `when`(
            service.update(
                productId = dummyProductId,
                request = request
            )
        ).thenReturn(dummyProductId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            productId = dummyProductId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyProductId)
    }

    @Test
    fun `상품 삭제`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        `when`(service.delete(productId = dummyProductId)).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(productId = dummyProductId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
