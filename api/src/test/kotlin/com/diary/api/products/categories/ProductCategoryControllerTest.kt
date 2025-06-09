package com.diary.api.products.categories

import com.diary.api.controller.v1.common.products.ProductCategoryController
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.categories.ProductCategoryResponseDto
import com.diary.api.service.products.ProductCategoryService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.categories.ProductCategoryOrderType
import com.diary.domain.entity.products.categories.ProductCategoryQueryFilter
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
@DisplayName("상품 카테고리 컨트롤러 단위 테스트")
class ProductCategoryControllerTest {

    private val dummyProductCategory: ProductCategory = DummyProductCategory.toEntity()
    private val dummyProductCategoryResponse: ProductCategoryResponseDto = DummyProductCategory.toResponseDto()

    @Mock
    private lateinit var service: ProductCategoryService

    @InjectMocks
    private lateinit var controller: ProductCategoryController

    @Test
    fun `상품 카테고리 목록 조회`() {
        // given
        val queryFilter = ProductCategoryQueryFilter(
            name = null
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val orderTypes: List<ProductCategoryOrderType> = emptyList()
        val dummyProductCategoryResponses: List<ProductCategoryResponseDto> = listOf(dummyProductCategoryResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyProductCategoryResponses,
            totalCount = dummyProductCategoryResponses.size.toLong()
        )
        `when`(
            service.getProductCategories(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getProductCategories(
            name = null,
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
    fun `상품 카테고리 단건 조회`() {
        // given
        val dummyProductCategoryId: ID = dummyProductCategory.id
        `when`(service.getProductCategory(productCategoryId = dummyProductCategoryId)).thenReturn(dummyProductCategoryResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getProductCategory(
            productCategoryId = dummyProductCategoryId,
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyProductCategoryResponse)
    }
}
