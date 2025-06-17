package com.diary.api.products

import com.diary.api.controller.v1.common.products.ProductController
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.ProductResponseDto
import com.diary.api.service.products.ProductService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductOrderType
import com.diary.domain.entity.products.ProductQueryFilter
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
@DisplayName("상품 컨트롤러 단위 테스트")
class ProductControllerTest {

    private val dummyProduct: Product = DummyProduct.toEntity()
    private val dummyProductResponse: ProductResponseDto = DummyProduct.toResponseDto()

    @Mock
    private lateinit var service: ProductService

    @InjectMocks
    private lateinit var controller: ProductController

    @Test
    fun `상품 목록 조회`() {
        // given
        val queryFilter = ProductQueryFilter(
            productCategoryId = null,
            status = null,
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val orderTypes: List<ProductOrderType> = emptyList()
        val dummyProductResponses: List<ProductResponseDto> = listOf(dummyProductResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyProductResponses,
            totalCount = dummyProductResponses.size.toLong()
        )
        `when`(
            service.getProducts(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getProducts(
            productCategoryId = null,
            status = null,
            page = null,
            size = null,
            orderTypes = null
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(paginationResponse)
    }

    @Test
    fun `상품 단건 조회`() {
        // given
        val dummyProductId: ID = dummyProduct.id
        `when`(service.getProduct(productId = dummyProductId)).thenReturn(dummyProductResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getProduct(productId = dummyProductId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyProductResponse)
    }
}
