package com.daangn.api.categories

import com.daangn.api.controller.v1.common.categories.CategoryController
import com.daangn.api.dto.categories.CategoryResponseDto
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.categories.CategoryService
import com.daangn.api.service.categories.query.CategoryQueryCreator
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.categories.CategoryOrderType
import com.daangn.domain.entity.categories.CategoryQueryFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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
@DisplayName("카테고리 컨트롤러 단위 테스트")
class CategoryControllerTest {

    private lateinit var dummyCategoryResponse: CategoryResponseDto

    @Mock
    private lateinit var service: CategoryService

    @Mock
    private lateinit var queryCreator: CategoryQueryCreator

    @InjectMocks
    private lateinit var controller: CategoryController

    @BeforeEach
    fun setup() {
        dummyCategoryResponse = DummyCategory.toResponseDto()
    }

    @Test
    fun `카테고리 목록 조회`() {
        // given
        val queryFilter = CategoryQueryFilter(
            name = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<CategoryOrderType> = emptyList()
        val dummyCategoryResponses: List<CategoryResponseDto> = listOf(
            dummyCategoryResponse
        )
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyCategoryResponses,
            totalCount = dummyCategoryResponses.size.toLong()
        )
        `when`(
            queryCreator.createQueryFilter(
                name = null
            )
        ).thenReturn(queryFilter)
        `when`(
            queryCreator.createPaginationFilter(
                page = null,
                size = null
            )
        ).thenReturn(pagination)
        `when`(
            service.getCategories(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getCategories(
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
    fun `카테고리 단건 조회`() {
        // given
        val dummyCategoryId: String = dummyCategoryResponse.id
        `when`(
            service.getCategory(
                categoryId = dummyCategoryId,
            )
        ).thenReturn(dummyCategoryResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getCategory(
            categoryId = dummyCategoryId,
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyCategoryResponse)
    }
}
