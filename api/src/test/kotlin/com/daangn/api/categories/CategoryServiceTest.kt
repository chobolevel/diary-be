package com.daangn.api.categories

import com.daangn.api.dto.categories.CategoryResponseDto
import com.daangn.api.dto.categories.CreateCategoryRequestDto
import com.daangn.api.dto.categories.UpdateCategoryRequestDto
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.service.categories.CategoryService
import com.daangn.api.service.categories.converter.CategoryConverter
import com.daangn.api.service.categories.updater.CategoryUpdater
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.categories.Category
import com.daangn.domain.entity.categories.CategoryOrderType
import com.daangn.domain.entity.categories.CategoryQueryFilter
import com.daangn.domain.entity.categories.CategoryRepositoryWrapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("카테고리 서비스 단위 테스트")
class CategoryServiceTest {

    private lateinit var dummyCategory: Category
    private lateinit var dummyCategoryResponse: CategoryResponseDto

    @Mock
    private lateinit var repositoryWrapper: CategoryRepositoryWrapper

    @Mock
    private lateinit var converter: CategoryConverter

    @Mock
    private lateinit var updater: CategoryUpdater

    @InjectMocks
    private lateinit var service: CategoryService

    @BeforeEach
    fun setup() {
        dummyCategory = DummyCategory.toEntity()
        dummyCategoryResponse = DummyCategory.toResponseDto()
    }

    @Test
    fun `카테고리 등록`() {
        // given
        val request: CreateCategoryRequestDto = DummyCategory.toCreateRequestDto()
        `when`(converter.convert(request)).thenReturn(dummyCategory)
        `when`(repositoryWrapper.save(dummyCategory)).thenReturn(dummyCategory)

        // when
        val result: String = service.create(
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyCategory.id)
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
        val dummyCategories: List<Category> = listOf(
            dummyCategory
        )
        val dummyCategoryResponses: List<CategoryResponseDto> = listOf(
            dummyCategoryResponse
        )
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyCategories)
        `when`(
            repositoryWrapper.searchCount(
                queryFilter = queryFilter
            )
        ).thenReturn(dummyCategories.size.toLong())
        `when`(converter.convert(dummyCategories)).thenReturn(dummyCategoryResponses)

        // when
        val result: PaginationResponseDto = service.getCategories(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyCategoryResponses)
        assertThat(result.totalCount).isEqualTo(dummyCategoryResponses.size.toLong())
    }

    @Test
    fun `카테고리 단건 조회`() {
        // given
        val dummyCategoryId: String = dummyCategory.id
        `when`(repositoryWrapper.findById(dummyCategoryId)).thenReturn(dummyCategory)
        `when`(converter.convert(dummyCategory)).thenReturn(dummyCategoryResponse)

        // when
        val result: CategoryResponseDto = service.getCategory(
            categoryId = dummyCategoryId
        )

        // then
        assertThat(result).isEqualTo(dummyCategoryResponse)
    }

    @Test
    fun `카테고리 정보 수정`() {
        // given
        val dummyCategoryId: String = dummyCategory.id
        val request: UpdateCategoryRequestDto = DummyCategory.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(dummyCategoryId)).thenReturn(dummyCategory)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyCategory
            )
        ).thenReturn(dummyCategory)

        // when
        val result: String = service.update(
            categoryId = dummyCategoryId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyCategoryId)
    }

    @Test
    fun `카테고리 삭제`() {
        // given
        val dummyCategoryId: String = dummyCategory.id
        `when`(repositoryWrapper.findById(dummyCategoryId)).thenReturn(dummyCategory)

        // when
        val result: Boolean = service.delete(
            categoryId = dummyCategoryId
        )

        // then
        assertThat(result).isTrue()
    }
}
